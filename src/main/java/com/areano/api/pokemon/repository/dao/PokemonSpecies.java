package com.areano.api.pokemon.repository.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class PokemonSpecies {
	public ArrayList<FlavorTextEntry> flavor_text_entries;
	public Habitat habitat;
	public int id;
	public boolean is_baby;
	public boolean is_legendary;
	public boolean is_mythical;
	public String name;
	public ArrayList<Name> names;
	public int order;
	public ArrayList<PalParkEncounter> pal_park_encounters;
	public ArrayList<PokedexNumber> pokedex_numbers;
	public Shape shape;
	public ArrayList<Variety> varieties;
}


