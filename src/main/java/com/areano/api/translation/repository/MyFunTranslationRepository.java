package com.areano.api.translation.repository;

import com.areano.api.translation.repository.dao.Contents;
import com.areano.api.translation.repository.dao.FunTranslation;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Repository
@Slf4j
class MyFunTranslationRepository implements FunTranslationRepository {

	private final String translationBaseUri;
	private final RestTemplate restTemplate;

	MyFunTranslationRepository(@Value("${translation.uri.base}") String translationBaseUri, RestTemplate restTemplate) {
		this.translationBaseUri = translationBaseUri;
		this.restTemplate = restTemplate;
	}

	@Override
	public Optional<String> getTranslation(String text, TranslationType translationType) {

		val uri = UriComponentsBuilder.fromUriString(getUri(translationType))
				.queryParam("text", text)
				.build()
				.toUriString();

		val request = RequestEntity
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.build();

		try {
			log.info(String.format("Getting %s translation", translationType));

			val response = this.restTemplate.exchange(request, FunTranslation.class);
			return Optional.ofNullable(response.getBody())
					.map(FunTranslation::getContents)
					.map(Contents::getTranslated);
		} catch (RestClientException e) {
			log.error("There was an error while retrieving data from the translation api", e);
			return Optional.empty();
		}
	}

	private String getUri(TranslationType translationType) {
		switch (translationType) {
			case YODA:
				return translationBaseUri + "/yoda";
			case SHAKESPEARE:
				return translationBaseUri + "/shakespeare";
			default:
				throw new IllegalArgumentException("Translation type is not supported");
		}
	}

}


