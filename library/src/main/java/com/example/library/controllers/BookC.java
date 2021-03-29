package com.example.library.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.openapitools.api.BooksApi;
import org.openapitools.model.Author;
import org.openapitools.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

import com.example.library.dtos.AuthorDto;
import com.example.library.dtos.BookDto;
import com.example.library.services.BookService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j

public class BookC implements BooksApi {

	@Autowired
	BookService bookService;

	@Autowired
	ModelMapper modelMapper;
	
	/*@Scheduled(fixedDelay = 20000)
	public void whenWriteStringUsingBufferedWritter_thenCorrect() throws IOException {
		String EXTERNAL_FILE_PATH = "src/main/java/Knjige.txt";
		File file = Paths.get(EXTERNAL_FILE_PATH).toFile();
		String str = "Hello";
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(str);
		writer.close();
			
	}*/
	
	@Override
	public ResponseEntity<List<Book>> getBooks(Integer page) {
		List<BookDto> books = bookService.findPaginated(page == null?1:page);
		List<Book> booksApi = new ArrayList<Book>();
		for (BookDto book : books) {
			booksApi.add(modelMapper.map(book, Book.class));			
		}
		return new ResponseEntity<List<Book>>(booksApi, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Book>> booksByAuthor(Author authorDto, String title, Integer page) {
		AuthorDto author = modelMapper.map(authorDto, AuthorDto.class);
		List<BookDto> books = bookService.getByPublisherOrTitle(author, title, page==null?1:page);
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
	public ResponseEntity<List<Book>> searchBooks(String title, Integer page) {
		
		
		List<BookDto> books = bookService.searchByTitle(title, page==null?1:page);
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
