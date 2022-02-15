package com.areano.api.pokemon.repository;

import com.areano.api.pokemon.repository.dao.PokemonSpecies;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Repository
@Slf4j
class MyPokemonRepository implements PokemonRepository {

	private final RestTemplate restTemplate;
	private final String pokemonBaseUri;

	MyPokemonRepository(RestTemplate restTemplate, @Value("${pokemon.uri.base}") String pokemonBaseUri) {
		this.restTemplate = restTemplate;
		this.pokemonBaseUri = pokemonBaseUri;
	}

	@Override
	public Optional<PokemonSpecies> getPokemonSpecies(String name) {

		val request = RequestEntity
				.get(pokemonBaseUri + "/{name}", name)
				.accept(MediaType.APPLICATION_JSON)
				.build();

		try {
			log.info(String.format("Getting information for pokemon '%s'", name));

			val response = this.restTemplate.exchange(request, PokemonSpecies.class);
			return Optional.ofNullable(response.getBody());
		} catch (RestClientException e) {
			log.error("There was an error while retrieving data from the pokemon api", e);
			return Optional.empty();
		}

	}

}
