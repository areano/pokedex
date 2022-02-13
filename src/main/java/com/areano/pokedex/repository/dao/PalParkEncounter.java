package com.areano.pokedex.repository.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class PalParkEncounter {
	public Area area;
	public int base_score;
	public int rate;
}
