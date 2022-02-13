package com.areano.pokedex.service;

import com.areano.pokedex.repository.PokemonRepository;
import com.areano.pokedex.repository.dao.*;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class MyPokedexServiceTest {

	@Test
	void getPokemon() throws Exception {

		val repository = Mockito.mock(PokemonRepository.class);
		val service = new MyPokedexService(repository);

		val ps = new PokemonSpecies();
		ps.setName("mewtwo");
		ps.set_legendary(true);
		ps.setHabitat(new Habitat("rare", "https://pokeapi.co/api/v2/pokemon-habitat/5/"));
		ps.setFlavor_text_entries(new ArrayList<>() {
			{
				add(new FlavorTextEntry(
						"description",
						new Language("en", "url"),
						new Version("version", "url")
				));
			}
		});

		Mockito.when(repository.getPokemonSpecies("mewtwo")).thenReturn(Optional.of(ps));

		val pokemon = service.getPokemon("mewtwo");

		assertTrue(pokemon.isPresent());
		assertEquals("mewtwo", pokemon.get().getName());
		assertEquals("rare", pokemon.get().getHabitat());
		assertEquals("description", pokemon.get().getDescription());
		assertTrue(pokemon.get().isLegendary());

	}

	@Test
	void sanitisePokemonDescription() throws Exception {

		val repository = Mockito.mock(PokemonRepository.class);
		val service = new MyPokedexService(repository);

		val ps = new PokemonSpecies();
		ps.setName("mewtwo");
		ps.set_legendary(true);
		ps.setHabitat(new Habitat("rare", "https://pokeapi.co/api/v2/pokemon-habitat/5/"));
		ps.setFlavor_text_entries(new ArrayList<>() {
			{
				add(new FlavorTextEntry(
						"some\r\f\n\tdescription",
						new Language("en", "url"),
						new Version("version", "url")
				));
			}
		});

		Mockito.when(repository.getPokemonSpecies("mewtwo")).thenReturn(Optional.of(ps));

		val pokemon = service.getPokemon("mewtwo");

		assertTrue(pokemon.isPresent());
		assertEquals("some    description", pokemon.get().getDescription());

	}

}
