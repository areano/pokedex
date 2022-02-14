package com.areano.api.pokedex.service;

import com.areano.api.pokemon.repository.PokemonRepository;
import com.areano.api.pokemon.repository.dao.*;
import com.areano.api.translation.repository.FunTranslationRepository;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class MyPokedexServiceTest {

	private PokemonRepository pokemonRepository;
	private FunTranslationRepository funTranslationRepository;
	private MyPokedexService pokedexService;

	@BeforeEach
	void beforeEach() {
		pokemonRepository = mock(PokemonRepository.class);
		funTranslationRepository = mock(FunTranslationRepository.class);
		pokedexService = new MyPokedexService(pokemonRepository, funTranslationRepository);
	}

	@Test
	void shouldGetPokemonByName() throws Exception {

		val ps = createPokemonSpecies("mewtwo", "rare", "description", false);

		when(pokemonRepository.getPokemonSpecies("mewtwo")).thenReturn(Optional.of(ps));

		val pokemon = pokedexService.getPokemon("mewtwo");

		assertTrue(pokemon.isPresent());
		assertEquals("mewtwo", pokemon.get().getName());
		assertEquals("rare", pokemon.get().getHabitat());
		assertEquals("description", pokemon.get().getDescription());
		assertFalse(pokemon.get().isLegendary());

	}

	@Test
	void sanitisePokemonDescription() throws Exception {

		val ps = createPokemonSpecies("mewtwo", "rare", "some\r\f\n\tdescription", false);

		when(pokemonRepository.getPokemonSpecies("mewtwo")).thenReturn(Optional.of(ps));

		val pokemon = pokedexService.getPokemon("mewtwo");

		assertTrue(pokemon.isPresent());
		assertEquals("some    description", pokemon.get().getDescription());

	}

	@Test
	void shouldReturnPokemonTranslatedWithYodaIfHabitatIsRare() throws Exception {

		val ps = createPokemonSpecies("mewtwo", "cave", "original description", false);

		when(pokemonRepository.getPokemonSpecies("mewtwo")).thenReturn(Optional.of(ps));

		when(funTranslationRepository.getTranslation("original description", FunTranslationRepository.TranslationType.YODA))
				.thenReturn(Optional.of("yoda description"));

		val pokemon = pokedexService.getPokemonTranslated("mewtwo");

		assertTrue(pokemon.isPresent());
		assertEquals("yoda description", pokemon.get().getDescription());

	}

	@Test
	void shouldReturnPokemonTranslatedWithYodaIfLegendary() throws Exception {

		val ps = createPokemonSpecies("mewtwo", "rare", "original description", true);

		when(pokemonRepository.getPokemonSpecies("mewtwo")).thenReturn(Optional.of(ps));

		when(funTranslationRepository.getTranslation("original description", FunTranslationRepository.TranslationType.YODA))
				.thenReturn(Optional.of("yoda description"));

		val pokemon = pokedexService.getPokemonTranslated("mewtwo");

		assertTrue(pokemon.isPresent());
		assertEquals("yoda description", pokemon.get().getDescription());

	}

	@Test
	void shouldReturnPokemonTranslatedWithShakespeare() throws Exception {

		val ps = createPokemonSpecies("mewtwo", "rare", "original description", false);

		when(pokemonRepository.getPokemonSpecies("mewtwo")).thenReturn(Optional.of(ps));

		when(funTranslationRepository.getTranslation("original description", FunTranslationRepository.TranslationType.SHAKESPEARE))
				.thenReturn(Optional.of("shakespeare description"));

		val pokemon = pokedexService.getPokemonTranslated("mewtwo");

		assertTrue(pokemon.isPresent());
		assertEquals("shakespeare description", pokemon.get().getDescription());

	}

	@Test
	void shouldReturnStandardDescriptionIfItCannotTranslate() throws Exception {

		val ps = createPokemonSpecies("mewtwo", "rare", "original description", false);

		when(pokemonRepository.getPokemonSpecies("mewtwo")).thenReturn(Optional.of(ps));

		when(funTranslationRepository.getTranslation("original description", FunTranslationRepository.TranslationType.SHAKESPEARE))
				.thenReturn(Optional.empty());

		val pokemon = pokedexService.getPokemonTranslated("mewtwo");

		assertTrue(pokemon.isPresent());
		assertEquals("original description", pokemon.get().getDescription());

	}

	private static PokemonSpecies createPokemonSpecies(String name, String habitat, String description, boolean legendary) {
		val ps = new PokemonSpecies();
		ps.setName(name);
		ps.set_legendary(legendary);
		ps.setHabitat(new Habitat(habitat, "url"));
		ps.setFlavor_text_entries(new ArrayList<>() {
			{
				add(new FlavorTextEntry(
						description,
						new Language("en", "url"),
						new Version("version", "url")
				));
			}
		});
		return ps;
	}
}
