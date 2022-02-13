package com.areano.pokedex.repository;

import com.areano.pokedex.repository.dao.PokemonSpecies;

import java.util.Optional;

public interface PokemonRepository {
	Optional<PokemonSpecies> getPokemonSpecies(String name);
}
