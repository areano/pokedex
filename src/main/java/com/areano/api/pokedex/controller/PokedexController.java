package com.areano.api.pokedex.controller;

import com.areano.api.pokedex.service.PokedexService;
import com.areano.api.pokedex.service.model.Pokemon;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pokemon")
class PokedexController {

	private final PokedexService pokedexService;

	@GetMapping("/{name}")
	ResponseEntity<Pokemon> getPokemon(@PathVariable String name) {

		return this.pokedexService.getPokemon(name)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());

	}

	@GetMapping("/translated/{name}")
	ResponseEntity<Pokemon> getPokemonTranslated(@PathVariable String name) {

		return this.pokedexService.getPokemonTranslated(name)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());

	}

}
