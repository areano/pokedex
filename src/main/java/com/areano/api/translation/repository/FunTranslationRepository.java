package com.areano.api.translation.repository;

import java.util.Optional;

public interface FunTranslationRepository {
	Optional<String> getTranslation(String text, TranslationType translationType);
}


