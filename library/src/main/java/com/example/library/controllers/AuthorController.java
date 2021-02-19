package com.example.library.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.dtos.AuthorDto;
import com.example.library.entities.Author;
import com.example.library.services.AuthorService;

@RestController
@RequestMapping("authors")

public class AuthorController {
	
	@Autowired
	AuthorService authorService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@GetMapping()
	@ResponseBody
	public List<AuthorDto> getAuthors() {
		
		List<Author> authors = authorService.getAll();
		List<AuthorDto> authorDtos = new ArrayList<AuthorDto>();
		for (Author author : authors) {
			authorDtos.add(modelMapper.map(author, AuthorDto.class));
		}
						
			return authorDtos;
	}
	
	@GetMapping("/search")
	@ResponseBody
	public List<AuthorDto> searchAuthors(@RequestParam String name) {
		List<Author> authors =  authorService.searchByName(name);
		
		List<AuthorDto> authorDtos = new ArrayList<AuthorDto>();
		for (Author author: authors) {
			authorDtos.add(modelMapper.map(author, AuthorDto.class));
		}
					
		return authorDtos;
	}
	
	@GetMapping(path = "/{id}", produces = "application/json")
	@ResponseBody
	public AuthorDto getAuthorById(@PathVariable int id) {
		Author author = authorService.getById(id);
		AuthorDto authorDto = modelMapper.map(author, AuthorDto.class);
		return authorDto;
	}
	
	@PostMapping
	@ResponseBody
	public AuthorDto createAuthor(@RequestBody AuthorDto newAuthorDto) {
		Author author = modelMapper.map(newAuthorDto, Author.class);
		Author newAuthor = authorService.create(author);
	    return modelMapper.map(newAuthor, AuthorDto.class);
	}
	
	@DeleteMapping(path = "/{id}", produces = "application/json")
	@ResponseBody
    public String deleteAuthor(@PathVariable int id) {
		authorService.delete(id);
		return "Successfuly deleted.";
		
    }	
	
	@PutMapping(path="/{id}", produces = "application/json")
	@ResponseBody
	public AuthorDto updateAuthor(@PathVariable int id, @RequestBody AuthorDto newAuthorDto) {
		Author author = modelMapper.map(newAuthorDto, Author.class);		
		Author newAuthor = authorService.update(id, author);		
		return modelMapper.map(newAuthorDto, AuthorDto.class);	
	    
	}

}
