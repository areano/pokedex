package com.areano.api.pokemon.repository.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class PokedexNumber {
	public int entry_number;
	public Pokedex pokedex;
}
