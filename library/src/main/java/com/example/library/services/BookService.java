package com.example.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.entities.Book;
import com.example.library.exceptions.NotFoundException;
import com.example.library.repositories.BookRepository;

@Service

public class BookService {
	
	@Autowired
	BookRepository booksRepository;
	
	public List<Book> getAll()
	{
		return (List<Book>)booksRepository.findAll();
	}
	
	public Book getById(int id)
	{
		Optional<Book> bookOptional = booksRepository.findById(id);
		
		if(bookOptional.isPresent())
			return bookOptional.get();
		else
			throw new NotFoundException();
	}
	
	public List<Book> getByPublisherOrTitle(String publisher, String title)
	{
		return (List<Book>)booksRepository.findByPublisherOrTitle(publisher, title);
	}
	
	public List<Book> searchByTitle(String title)
	{
		return (List<Book>)booksRepository.searchByTitle(title);
	}
	
	public Book create(Book newBook)
	{
		return booksRepository.save(newBook);
	}
	
	public void delete(int id)
	{
		Book book = getById(id);		
		booksRepository.delete(book);
	}
	
	public Book update(int id, Book newBook)
	{
		Book book = getById(id);		
		book.setNumPages(newBook.getNumPages());
		book.setPublicationYear(newBook.getPublicationYear());
		book.setPublisher(newBook.getPublisher());
		book.setTitle(newBook.getTitle());
		
		return booksRepository.save(book);
	}

}
