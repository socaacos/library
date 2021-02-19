package com.example.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.entities.Author;
import com.example.library.entities.Book;
import com.example.library.exceptions.BookNotFoundException;
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
			throw new BookNotFoundException();
	}
	
	public List<Book> getByPublisherOrTitle(Author author, String title)
	{
		return (List<Book>)booksRepository.findByAuthorOrTitle(author, title);
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
		book.setAuthor(newBook.getAuthor());
		book.setTitle(newBook.getTitle());
		
		return booksRepository.save(book);
	}

}
