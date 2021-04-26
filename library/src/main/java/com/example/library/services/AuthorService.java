package com.example.library.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.dtos.AuthorDto;
import com.example.library.entities.Author;
import com.example.library.exceptions.BookNotFoundException;
import com.example.library.repositories.AuthorRepository;
import com.example.library.repositories.BookRepository;
@Service

public class AuthorService {

	@Autowired
	AuthorRepository  authorRepository;
	
	@Autowired
	BookRepository  bookRepository;
	
	@Autowired
	KafkaSender kafkaSender;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	public List<AuthorDto> getAll()
	{
		List<Author> authors = (List<Author>) authorRepository.findAll();
		List<AuthorDto> authorDtos = new ArrayList<AuthorDto>();
		for (Author author : authors) {
			authorDtos.add(modelMapper.map(author, AuthorDto.class));
		}
		return authorDtos;		
	}
	
	public AuthorDto getById(int id)
	{
		Optional<Author> author = authorRepository.findById(id);
		
		if(author.isPresent())
		{
			AuthorDto authorDto = modelMapper.map(author.get(), AuthorDto.class);
			kafkaSender.sendData(authorDto);
			return authorDto;
		}
			
		else
			throw new BookNotFoundException();	
	}
	
	public List<AuthorDto> searchByName(String name)
	{
		List<Author> authors= (List<Author>) authorRepository.searchByName(name);
		List<AuthorDto> authorDtos = new ArrayList<AuthorDto>();
		for (Author author : authors) {
			authorDtos.add(modelMapper.map(author, AuthorDto.class));
		}
		return authorDtos;
	}
	
	public AuthorDto create(AuthorDto newAuthorDto)
	{
		Author author = modelMapper.map(newAuthorDto, Author.class);
		Author newAuthor= authorRepository.save(author);
	    return modelMapper.map(newAuthor, AuthorDto.class);	}
	
	public boolean delete(int id)
	{
		AuthorDto authorDto = getById(id);
		Author author = modelMapper.map(authorDto, Author.class);
		if(author != null) {
			bookRepository.deleteByAuthor(author);
			authorRepository.delete(author);
			return true;
		}
		else
			return false;
	}
	
	public AuthorDto update(int id, AuthorDto newAuthorDto)
	{
		AuthorDto authorDto = getById(id);
		authorDto.setName(newAuthorDto.getName());
		Author author = modelMapper.map(authorDto, Author.class);
		authorRepository.save(author);
		return modelMapper.map(author, AuthorDto.class);
	}

}
