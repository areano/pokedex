package com.areano.pokedex.repository;

import com.areano.pokedex.repository.model.PokemonSpecies;

import java.util.Optional;

public interface PokemonRepository {
	Optional<PokemonSpecies> getPokemonSpecies(String name);
}
