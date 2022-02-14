package com.areano.api.pokemon.repository;

import com.areano.api.pokemon.repository.dao.PokemonSpecies;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
class MyPokemonRepository implements PokemonRepository {

	private final RestTemplate restTemplate;

	@Override
	public Optional<PokemonSpecies> getPokemonSpecies(String name) {

		val request = RequestEntity
				.get("https://pokeapi.co/api/v2/pokemon-species/{name}", name)
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
