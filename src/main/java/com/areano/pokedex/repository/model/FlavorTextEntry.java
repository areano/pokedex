package com.areano.pokedex.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class FlavorTextEntry {
	public String flavor_text;
	public Language language;
	public Version version;
}
