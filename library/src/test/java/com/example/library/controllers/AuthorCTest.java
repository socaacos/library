package com.example.library.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.openapitools.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.library.dtos.AuthorDto;
import com.example.library.services.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorCTest {

	@MockBean
	AuthorService service;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ModelMapper modelMaper;
	
	@Test
	void getAllCTest() throws Exception {
		AuthorDto a1 = new AuthorDto("Sofija");
		AuthorDto a2 = new AuthorDto("Viktorija");
		doReturn(Lists.newArrayList(a1, a2)).when(service).getAll();
		System.out.println(service.getAll().size());
		mockMvc.perform(get("/authors")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].name", is("Sofija"))).andExpect(jsonPath("$[1].name", is("Viktorija")));
	}

	@Test
	void getByIdCTest() throws Exception {
		AuthorDto author = new AuthorDto(1, "Sofija");
		int id = 1;
		when(service.getById(id)).thenReturn(author);

		mockMvc.perform(get("/authors/{id}", 1)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("Sofija")));
	}

	@Test
	void getByNameCTest() throws Exception {
		AuthorDto author1 = new AuthorDto("Sofija");
		AuthorDto author2 = new AuthorDto("Sofija");
		String name = "Sofija";
		when(service.searchByName(name)).thenReturn(Lists.newArrayList(author1, author2));
		mockMvc.perform(get("/authors/search?name=Sofija")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].name", is("Sofija"))).andExpect(jsonPath("$[1].name", is("Sofija")));
	}

	@Test
	void createCTest() throws Exception {
		AuthorDto aPost = new AuthorDto("Sofija");
		AuthorDto aReturn = new AuthorDto(1, "Sofija");
		Author a = modelMaper.map(aPost, Author.class);
		doReturn(aReturn).when(service).create(any());

		mockMvc.perform(post("/authors").contentType(MediaType.APPLICATION_JSON).content(asJsonString(a)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Sofija")));
	}

	@Test
	void deleteCTest() throws Exception {
		int id = 1;
		AuthorDto aReturnFindBy = new AuthorDto(1, "Sofija");
		when(service.getById(id)).thenReturn(aReturnFindBy);
		when(service.delete(id)).thenReturn(true);
		
		mockMvc.perform(delete("/authors/{id}", 1)).andExpect(status().isOk());
	}

	@Test
	void updateCTest() throws Exception {
		AuthorDto aPut = new AuthorDto("Sofija");
		AuthorDto aReturnFindBy = new AuthorDto(1, "Sofija");
		AuthorDto aReturnSAVE = new AuthorDto(1, "Sofija");
		int id = 1;
		when(service.getById(id)).thenReturn(aReturnFindBy);
		when(service.update(id, aPut)).thenReturn(aReturnSAVE);
		when(service.create(any())).thenReturn(aReturnSAVE);

		mockMvc.perform(put("/authors/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(asJsonString(aPut)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Sofija")));
	}

	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
