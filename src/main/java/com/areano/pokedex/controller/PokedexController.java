package com.areano.pokedex.controller;

import com.areano.pokedex.service.PokedexService;
import com.areano.pokedex.service.Pokemon;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class PokedexController {

	private final PokedexService pokedexService;

	@GetMapping("/pokemon/{name}")
	ResponseEntity<Pokemon> getPokemon(@PathVariable String name) {

		return this.pokedexService.getPokemon(name)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());

	}

}
