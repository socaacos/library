
package com.example.library.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.library.entities.Book;
import com.example.library.repositories.BookRepository;
import com.example.library.services.BookService;

@RestController
@RequestMapping("books")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@GetMapping()	
	public ResponseEntity<List<Book>> getBooks(@RequestParam(required = false) String publisher, @RequestParam(required = false) String title) {
		
		if (publisher == null && title == null)
			return ResponseEntity.ok(bookService.getAll());
		
		return ResponseEntity.ok(bookService.getByPublisherOrTitle(publisher, title));
	}
	
	@GetMapping("/search")	
	public ResponseEntity<List<Book>> searchBooks(@RequestParam String title) {
		return ResponseEntity.ok(bookService.searchByTitle(title));
	}
	
	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<Book> getBookById(@PathVariable int id) {
		Book book = bookService.getById(id);
		return ResponseEntity.ok(book);	
	}
	
	@PostMapping
	public ResponseEntity<Book> createBook(@RequestBody Book newBook) {
		
		Book book = bookService.create(newBook);
	    return ResponseEntity.ok(book);
	}
	
	@DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
		bookService.delete(id);
		return ResponseEntity.ok("Successfuly deleted.");
		
    }
	
	@PutMapping(path="/{id}", produces = "application/json")
	public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book newBook) {
		
		Book book = bookService.update(id, newBook);
		
		return ResponseEntity.ok(book);	
	    
	}

}
