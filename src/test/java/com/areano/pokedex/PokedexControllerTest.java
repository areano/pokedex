package com.areano.pokedex;

import com.areano.pokedex.service.Pokemon;
import com.areano.pokedex.service.PokedexService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class PokedexControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PokedexService pokedexService;

	@Test
	void shouldReturnPokemonInformation() throws Exception {

		Mockito.when(this.pokedexService.getPokemon("name")).thenReturn(Optional.of(Pokemon.builder()
				.name("name")
				.description("description")
				.habitat("habitat")
				.isLegendary(true)
				.build()));

		this.mockMvc.perform(MockMvcRequestBuilders
						.get("/pokemon/name")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.habitat").value("habitat"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.legendary").value(true));

	}

	@Test
	void shouldReturnNotFoundIfThePokemonDoesNotExists() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders
						.get("/pokemon/name")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound());

	}
}
