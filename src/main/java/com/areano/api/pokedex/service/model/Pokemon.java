package com.areano.api.pokedex.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pokemon {
	private String name;
	private String description;
	private String habitat;
	private boolean isLegendary;
}
