package com.example.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.entities.Author;
import com.example.library.exceptions.BookNotFoundException;
import com.example.library.repositories.AuthorRepository;
@Service

public class AuthorService {
	
	@Autowired
	AuthorRepository  authorRepository;
	
	public List<Author> getAll()
	{
		return (List<Author>) authorRepository.findAll();
	}
	
	public Author getById(int id)
	{
		Optional<Author> author = authorRepository.findById(id);
		
		if(author.isPresent())
			return author.get();
		else
			throw new BookNotFoundException();
	}
	
	public List<Author> searchByName(String name)
	{
		return (List<Author>)authorRepository.searchByName(name);
	}
	
	public Author create(Author newAuthor)
	{
		return authorRepository.save(newAuthor);
	}
	
	public void delete(int id)
	{
		Author author = getById(id);
		if(author != null)
			authorRepository.delete(author);
		else
			System.out.println("Nema autora");
	}
	
	public Author update(int id, Author newAuthor)
	{
		Author author = getById(id);
		author.setName(newAuthor.getName());
		
		return authorRepository.save(author);
	}

}
