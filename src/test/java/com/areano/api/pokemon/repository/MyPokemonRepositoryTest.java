package com.areano.api.pokemon.repository;

import com.areano.api.pokemon.repository.dao.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.env.PropertyResolver;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.MockRestServiceServer.createServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@RestClientTest(MyPokemonRepository.class)
@AutoConfigureWebClient(registerRestTemplate = true)
class MyPokemonRepositoryTest {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MockRestServiceServer mockRestServiceServer;

	@Autowired
	private MyPokemonRepository myPokemonRepository;

	@Autowired
	private PropertyResolver propertyResolver;

	private final ObjectMapper mapper = new ObjectMapper();
	private String baseUri;

	@BeforeEach
	void beforeEach() {
		mockRestServiceServer = createServer(this.restTemplate);
		baseUri = propertyResolver.getProperty("pokemon.uri.base");
	}

	@Test
	void shouldReturnParsedDataFromPokemonApi() throws Exception {

		val ps = new PokemonSpecies();
		ps.setName("mewtwo");
		ps.set_legendary(true);
		ps.setHabitat(new Habitat("rare", "https://pokeapi.co/api/v2/pokemon-habitat/5/"));
		ps.setFlavor_text_entries(new ArrayList<>() {
			{
				add(new FlavorTextEntry(
						"text",
						new Language("en", "url"),
						new Version("version", "url")
				));
			}
		});


		mockRestServiceServer.expect(once(), requestTo(new URI(baseUri + "/mewtwo")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON)
						.body(mapper.writeValueAsString(ps))
				);

		val response = myPokemonRepository.getPokemonSpecies("mewtwo");
		assertTrue(response.isPresent());
		assertEquals(response.get(), ps);

	}

	@Test
	void shouldReturnEmptyIfResponseIsDifferentThan2xx() throws Exception {

		mockRestServiceServer = MockRestServiceServer.createServer(this.restTemplate);

		mockRestServiceServer.expect(once(), requestTo(new URI(baseUri + "/mewtwo")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.NOT_FOUND));

		val response = myPokemonRepository.getPokemonSpecies("mewtwo");
		assertFalse(response.isPresent());

	}

}
