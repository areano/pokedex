package com.areano.pokedex.service;

import com.areano.pokedex.repository.PokemonRepository;
import com.areano.pokedex.repository.model.FlavorTextEntry;
import com.areano.pokedex.repository.model.PokemonSpecies;
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

	@Override
	public Optional<Pokemon> getPokemon(String name) {
		return pokemonRepository.getPokemonSpecies(name)
				.map(MyPokedexService::createPokemonFromPokemonSpecies);
	}

	private static Pokemon createPokemonFromPokemonSpecies(PokemonSpecies pokemonSpecies) {
		val pb = Pokemon.builder()
				.name(pokemonSpecies.getName())
				.habitat(pokemonSpecies.getHabitat().getName())
				.isLegendary(pokemonSpecies.is_legendary);

		getFirstDescriptionInEnglish(pokemonSpecies.getFlavor_text_entries())
				.ifPresent(pb::description);

		return pb.build();
	}

	private static Optional<String> getFirstDescriptionInEnglish(List<FlavorTextEntry> entries) {
		return entries.stream()
				.filter(e -> Objects.equals(e.getLanguage().getName(), "en"))
				.findFirst()
				.map(FlavorTextEntry::getFlavor_text);
	}

}
