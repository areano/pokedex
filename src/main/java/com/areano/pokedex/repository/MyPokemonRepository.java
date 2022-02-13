package com.areano.pokedex.repository;

import com.areano.pokedex.repository.model.PokemonSpecies;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class MyPokemonRepository implements PokemonRepository {

	private final RestTemplate restTemplate;

	@Override
	@SneakyThrows
	public Optional<PokemonSpecies> getPokemonSpecies(String name) {

		val request = RequestEntity
				.get("https://pokeapi.co/api/v2/pokemon-species/{name}", name)
				.accept(MediaType.APPLICATION_JSON)
				.build();

		val response = this.restTemplate.exchange(request, PokemonSpecies.class);

		return Optional.ofNullable(response.getBody());
	}

}