package com.areano.api.pokemon.repository;

import com.areano.api.pokemon.repository.dao.PokemonSpecies;

import java.util.Optional;

public interface PokemonRepository {
	Optional<PokemonSpecies> getPokemonSpecies(String name);
}
