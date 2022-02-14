package com.areano.pokedex.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MyFunTranslationRepository implements FunTranslationRepository{
	@Override
	public Optional<String> getTranslation(String text, TranslationType translationType) {
		return Optional.empty();
	}
}
