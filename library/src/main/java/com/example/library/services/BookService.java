package com.example.library.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.dtos.AuthorDto;
import com.example.library.dtos.BookDto;
import com.example.library.entities.Author;
import com.example.library.entities.Book;
import com.example.library.exceptions.BookNotFoundException;
import com.example.library.repositories.BookRepository;

@Service

public class BookService {
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public List<BookDto> getAll()
	{
		List<Book> books = (List<Book>) bookRepository.findAll();
		List<BookDto> bookDtos = new ArrayList<BookDto>();
		for (Book book : books) {
			bookDtos.add(modelMapper.map(book, BookDto.class));
		}
		return bookDtos;	
	}
	
	public BookDto getById(int id)
	{
		Optional<Book> book = bookRepository.findById(id);
		BookDto bookDto = modelMapper.map(book, BookDto.class);
		if(bookDto != null)
			return bookDto;
		else
			throw new BookNotFoundException();
	}
	
	public List<BookDto> getByPublisherOrTitle(AuthorDto authorDto, String title)
	{
		Author author = modelMapper.map(authorDto, Author.class);
		List<Book> books = (List<Book>) bookRepository.findByAuthorOrTitle(author, title);
		List<BookDto> bookDtos = new ArrayList<BookDto>();
		for (Book book : books) {
			bookDtos.add(modelMapper.map(book, BookDto.class));
		}
		return bookDtos;
	}
	
	public List<BookDto> searchByTitle(String title)
	{
		List<Book> books = (List<Book>) bookRepository.searchByTitle(title);
		List<BookDto> bookDtos = new ArrayList<BookDto>();
		for (Book book : books) {
			bookDtos.add(modelMapper.map(book, BookDto.class));
		}
		return bookDtos;
	}
	
	public BookDto create(BookDto newBookDto)
	{
		Book book = modelMapper.map(newBookDto, Book.class);
		Book newBook = bookRepository.save(book);
	    return modelMapper.map(newBook, BookDto.class);
	}
	
	public void delete(int id)
	{
		BookDto bookDto = getById(id);
		Book book = modelMapper.map(bookDto, Book.class);
		if(book != null)
			bookRepository.delete(book);
		else
			System.out.println("Nema knjige");
	}
	
	
	public BookDto update(int id, BookDto newBookDto)
	{
		BookDto bookDto = getById(id);
		bookDto.setNumPages(newBookDto.getNumPages());
		bookDto.setPublicationYear(newBookDto.getPublicationYear());
		bookDto.setLibraries(newBookDto.getLibraries());
		bookDto.setAuthor(newBookDto.getAuthor());
		bookDto.setTitle(newBookDto.getTitle());
		Book book = modelMapper.map(bookDto, Book.class);
		bookRepository.save(book);
		return modelMapper.map(book, BookDto.class);
		
	}

}
