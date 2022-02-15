package com.areano.api.translation.repository;

import com.areano.api.translation.repository.dao.Contents;
import com.areano.api.translation.repository.dao.FunTranslation;
import com.areano.api.translation.repository.dao.Success;
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

	@Autowired
	private PropertyResolver propertyResolver;

	private final ObjectMapper mapper = new ObjectMapper();
	private String baseUri;

	@BeforeEach
	void beforeEach() {
		mockRestServiceServer = createServer(this.restTemplate);
		baseUri = propertyResolver.getProperty("translation.uri.base");
	}

	@Test
	void shouldReturnYodaTranslation() throws Exception {

		val translation = new FunTranslation();
		translation.setSuccess(new Success(1));
		translation.setContents(new Contents("text translated", "text to translate", "yoda"));

		val uri = UriComponentsBuilder.fromUriString(baseUri + "/yoda")
				.queryParam("text", "text to translate")
				.toUriString();

		mockRestServiceServer.expect(once(), requestTo(uri))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON)
						.body(mapper.writeValueAsString(translation))
				);

		val response = myFunTranslationRepository.getTranslation("text to translate", TranslationType.YODA);
		assertTrue(response.isPresent());
		assertEquals(translation.getContents().getTranslated(), response.get());

	}

	@Test
	void shouldReturnShakespeareTranslation() throws Exception {

		val translation = new FunTranslation();
		translation.setSuccess(new Success(1));
		translation.setContents(new Contents("text translated", "text to translate", "yoda"));

		val uri = UriComponentsBuilder.fromUriString(baseUri + "/shakespeare")
				.queryParam("text", "text to translate")
				.toUriString();

		mockRestServiceServer.expect(once(), requestTo(uri))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON)
						.body(mapper.writeValueAsString(translation))
				);

		val response = myFunTranslationRepository.getTranslation("text to translate", TranslationType.SHAKESPEARE);
		assertTrue(response.isPresent());
		assertEquals(translation.getContents().getTranslated(), response.get());

	}

}
