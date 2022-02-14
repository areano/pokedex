package com.areano.pokedex.service;

import java.util.Optional;

public interface PokedexService {
	Optional<Pokemon> getPokemon(String name);

	Optional<Pokemon> getPokemonTranslated(String name);
}
