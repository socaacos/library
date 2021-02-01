
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

import com.example.library.dtos.BookDto;
import com.example.library.entities.Book;
import com.example.library.services.BookService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("books")
@Slf4j
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@GetMapping()
	@ResponseBody
	public List<BookDto> getBooks(@RequestParam(required = false) String publisher, @RequestParam(required = false) String title) {
		
		if (publisher == null && title == null)
		{
			List<Book> books = bookService.getAll();
			List<BookDto> bookDtos = new ArrayList<BookDto>();
			for (Book book : books) {
				bookDtos.add(modelMapper.map(book, BookDto.class));
			}
						
			return bookDtos;
		}
		List<Book> books = bookService.getByPublisherOrTitle(publisher, title);
		List<BookDto> bookDtos = new ArrayList<BookDto>();
		for (Book book : books) {
			bookDtos.add(modelMapper.map(book, BookDto.class));
		}
					
		return bookDtos;
	}
	
	@GetMapping("/search")
	@ResponseBody
	public List<BookDto> searchBooks(@RequestParam String title) {
		List<Book> books =  bookService.searchByTitle(title);
		
		List<BookDto> bookDtos = new ArrayList<BookDto>();
		for (Book book : books) {
			bookDtos.add(modelMapper.map(book, BookDto.class));
		}
					
		return bookDtos;
	}
	
	@GetMapping(path = "/{id}", produces = "application/json")
	@ResponseBody
	public BookDto getBookById(@PathVariable int id) {
		Book book = bookService.getById(id);
		BookDto bookDto = modelMapper.map(book, BookDto.class);
		return bookDto;
	}
	
	@PostMapping
	@ResponseBody
	public BookDto createBook(@RequestBody BookDto newBookDto) {
		Book book = modelMapper.map(newBookDto, Book.class);
		Book newBook = bookService.create(book);
	    return modelMapper.map(newBook, BookDto.class);
	}
	
	@DeleteMapping(path = "/{id}", produces = "application/json")
	@ResponseBody
    public String deleteBook(@PathVariable int id) {
		bookService.delete(id);
		return "Successfuly deleted.";
		
    }	
	
	@PutMapping(path="/{id}", produces = "application/json")
	@ResponseBody
	public BookDto updateBook(@PathVariable int id, @RequestBody BookDto newBookDto) {
		Book book = modelMapper.map(newBookDto, Book.class);		
		Book newBook = bookService.update(id, book);		
		return modelMapper.map(newBook, BookDto.class);	
	    
	}

}
