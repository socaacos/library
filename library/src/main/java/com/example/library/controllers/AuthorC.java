package com.example.library.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.openapitools.api.AuthorsApi;
import org.openapitools.model.Author;
import org.openapitools.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

import com.example.library.dtos.AuthorDto;
import com.example.library.dtos.CityDto;
import com.example.library.services.AuthorService;
import com.example.library.services.KafkaSender;

@Controller
@RefreshScope

public class AuthorC implements AuthorsApi {
	
	@Autowired
	AuthorService authorService;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	@Override
	public ResponseEntity<List<Author>> getAuthors() {
		List<AuthorDto> authors = authorService.getAll();
		List<Author> authorsApi = new ArrayList<Author>();
		for (AuthorDto author : authors) {
			authorsApi.add(modelMapper.map(author, Author.class));			
		}
		return new ResponseEntity<List<Author>>(authorsApi, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Author> getAuthorById(Integer id) {
		AuthorDto authorDto = authorService.getById(id);
		Author authorApi = modelMapper.map(authorDto, Author.class);
		return new ResponseEntity<Author>(authorApi, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<List<Author>> searchAuthors(String name) {
		List<AuthorDto> authors = authorService.searchByName(name);
		List<Author> authorsApi = new ArrayList<Author>();
		for (AuthorDto author : authors) {
			authorsApi.add(modelMapper.map(author, Author.class));			
		}
		return new ResponseEntity<List<Author>>(authorsApi, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Author> createAuthor(Author author) {
		System.out.println(author);
		AuthorDto authorDto = modelMapper.map(author, AuthorDto.class);
		AuthorDto newAuthor = authorService.create(authorDto);
		Author authorApi = modelMapper.map(newAuthor, Author.class);
		return new ResponseEntity<Author>(authorApi, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<String> deleteAuthor(Integer id) {
		authorService.delete(id);
		return new ResponseEntity<String>("Deleted", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Author> updateAuthor(Integer id, Author author) {
		AuthorDto authorDto = modelMapper.map(author, AuthorDto.class);
		AuthorDto newAuthor = authorService.update(id, authorDto);
		Author authorApi = modelMapper.map(newAuthor, Author.class);
		return new ResponseEntity<Author>(authorApi, HttpStatus.OK);
	}
	
	
	
}
