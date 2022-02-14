package com.areano.pokedex.repository;

import java.util.Optional;

public interface FunTranslationRepository {
	Optional<String> getTranslation(String text, TranslationType translationType);

	enum TranslationType {
		SHAKESPEARE, YODA
	}
}


