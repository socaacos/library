package com.example.library.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.openapitools.api.BooksApi;
import org.openapitools.model.Author;
import org.openapitools.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.example.library.dtos.AuthorDto;
import com.example.library.dtos.BookDto;
import com.example.library.services.BookService;

@Controller

public class BookC implements BooksApi {

	@Autowired
	BookService bookService;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public ResponseEntity<List<Book>> getBooks() {
		List<BookDto> books = bookService.getAll();
		List<Book> booksApi = new ArrayList<Book>();
		for (BookDto book : books) {
			booksApi.add(modelMapper.map(book, Book.class));			
		}
		return new ResponseEntity<List<Book>>(booksApi, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<List<Book>> booksByAuthor(Author authorDto, String title) {
		AuthorDto author = modelMapper.map(authorDto, AuthorDto.class);
		List<BookDto> books = bookService.getByPublisherOrTitle(author, title);
		List<Book> booksApi = new ArrayList<Book>();
		for (BookDto book : books) {
			booksApi.add(modelMapper.map(book, Book.class));			
		}
		return new ResponseEntity<List<Book>>(booksApi, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Book> getBookById(Integer id) {
		BookDto bookDto = bookService.getById(id);
		Book bookApi = modelMapper.map(bookDto, Book.class);
		return new ResponseEntity<Book>(bookApi, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Book>> searchBooks(String title) {
		List<BookDto> books = bookService.searchByTitle(title);
		List<Book> booksApi = new ArrayList<Book>();
		for (BookDto book : books) {
			booksApi.add(modelMapper.map(book, Book.class));
		}
		return new ResponseEntity<List<Book>>(booksApi, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteBook(Integer id) {
		bookService.delete(id);
		return new ResponseEntity<String>("Deleted", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Book> createBook(Book book) {
		BookDto bookDto = modelMapper.map(book, BookDto.class);
		BookDto newBook = bookService.create(bookDto);
		Book bookApi = modelMapper.map(newBook, Book.class);
		return new ResponseEntity<Book>(bookApi, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Book> updateBook(Integer id, Book book) {
		BookDto bookDto = modelMapper.map(book, BookDto.class);
		BookDto newBook = bookService.update(id, bookDto);
		Book bookApi = modelMapper.map(newBook, Book.class);
		return new ResponseEntity<Book>(bookApi, HttpStatus.OK);
	}

}
