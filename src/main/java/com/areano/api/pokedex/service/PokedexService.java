package com.areano.api.pokedex.service;

import com.areano.api.pokedex.service.model.Pokemon;

import java.util.Optional;

public interface PokedexService {
	Optional<Pokemon> getPokemon(String name);

	Optional<Pokemon> getPokemonTranslated(String name);
}
