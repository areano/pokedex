package com.areano.pokedex.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.MockRestServiceServer.createServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@RestClientTest(MyFunTranslationRepository.class)
@AutoConfigureWebClient(registerRestTemplate = true)
class MyFunTranslationRepositoryTest {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MockRestServiceServer mockRestServiceServer;

	@Autowired
	private MyFunTranslationRepository myFunTranslationRepository;

	private final ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	void beforeEach() {
		mockRestServiceServer = createServer(this.restTemplate);
	}

	@Test
	void shouldReturnYodaTranslation() throws Exception {

		val translation = new FunTranslationResponse();
		translation.setSuccess(new Success(1));
		translation.setContents(new Contents("text translated", "text to translate", "yoda"));

		val uri = UriComponentsBuilder.fromUriString("https://api.funtranslations.com/translate/yoda")
				.queryParam("text", "text to translate")
				.toUriString();

		mockRestServiceServer.expect(once(), requestTo(uri))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON)
						.body(mapper.writeValueAsString(translation))
				);

		val response = myFunTranslationRepository.getTranslation("text to translate", FunTranslationRepository.TranslationType.YODA);
		assertTrue(response.isPresent());
		assertEquals(translation.getContents().getTranslated(), response.get());

	}

	@Test
	void shouldReturnShakespeareTranslation() throws Exception {

		val translation = new FunTranslationResponse();
		translation.setSuccess(new Success(1));
		translation.setContents(new Contents("text translated", "text to translate", "yoda"));

		val uri = UriComponentsBuilder.fromUriString("https://api.funtranslations.com/translate/shakespeare")
				.queryParam("text", "text to translate")
				.toUriString();

		mockRestServiceServer.expect(once(), requestTo(uri))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON)
						.body(mapper.writeValueAsString(translation))
				);

		val response = myFunTranslationRepository.getTranslation("text to translate", FunTranslationRepository.TranslationType.SHAKESPEARE);
		assertTrue(response.isPresent());
		assertEquals(translation.getContents().getTranslated(), response.get());

	}

}
