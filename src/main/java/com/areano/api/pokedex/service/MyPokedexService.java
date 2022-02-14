package com.areano.api.pokedex.service;

import com.areano.api.pokedex.service.model.Pokemon;
import com.areano.api.pokemon.repository.PokemonRepository;
import com.areano.api.pokemon.repository.dao.FlavorTextEntry;
import com.areano.api.pokemon.repository.dao.PokemonSpecies;
import com.areano.api.translation.repository.FunTranslationRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class MyPokedexService implements PokedexService {

	private final PokemonRepository pokemonRepository;
	private final FunTranslationRepository funTranslationRepository;

	@Override
	public Optional<Pokemon> getPokemon(String name) {
		return getPokemon(name, false);
	}

	@Override
	public Optional<Pokemon> getPokemonTranslated(String name) {
		return getPokemon(name, true);
	}

	private Optional<Pokemon> getPokemon(String name, boolean translate) {
		return pokemonRepository.getPokemonSpecies(name)
				.map(pokemonSpecies -> createPokemonFromPokemonSpecies(pokemonSpecies, translate));
	}

	private Pokemon createPokemonFromPokemonSpecies(PokemonSpecies pokemonSpecies, boolean translate) {
		val habitat = pokemonSpecies.getHabitat().getName();
		val isLegendary = pokemonSpecies.is_legendary;
		val pb = Pokemon.builder()
				.name(pokemonSpecies.getName())
				.habitat(habitat)
				.isLegendary(isLegendary);

		getFirstDescriptionInEnglish(pokemonSpecies.getFlavor_text_entries())
				.map(MyPokedexService::sanitiseDescription)
				.map(description -> translate ?
						translateDescription(description, habitat, isLegendary).orElse(description) :
						description
				)
				.ifPresent(pb::description);

		return pb.build();
	}

	private Optional<String> translateDescription(String description, String habitat, boolean isLegendary) {
		if (habitat.equalsIgnoreCase("cave") || isLegendary) {
			return this.funTranslationRepository.getTranslation(description, FunTranslationRepository.TranslationType.YODA);
		}
		return this.funTranslationRepository.getTranslation(description, FunTranslationRepository.TranslationType.SHAKESPEARE);
	}

	private static Optional<String> getFirstDescriptionInEnglish(List<FlavorTextEntry> entries) {
		return entries.stream()
				.filter(e -> Objects.equals(e.getLanguage().getName(), "en"))
				.findFirst()
				.map(FlavorTextEntry::getFlavor_text);
	}

	private static String sanitiseDescription(String description) {
		return description
				.replaceAll("[\n\f\r\t]", " ");
	}
}
