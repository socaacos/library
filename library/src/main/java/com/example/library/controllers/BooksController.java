
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
import com.example.library.repositories.BooksRepository;

@RestController
@RequestMapping("books")
public class BooksController {

	@Autowired
	BooksRepository booksRepository;
	
	@GetMapping()	
	public ResponseEntity<List<Book>> getBooks(@RequestParam(required = false) String publisher, @RequestParam(required = false) String title) {
		
		if (publisher == null && title == null)
			return ResponseEntity.ok((List<Book>) booksRepository.findAll());
		
		return ResponseEntity.ok((List<Book>) booksRepository.findByPublisherOrTitle(publisher, title));
	}
	
	@GetMapping("/search")	
	public ResponseEntity<List<Book>> searchBooks(@RequestParam String title) {
		return ResponseEntity.ok((List<Book>) booksRepository.searchByTitle(title));
	}
	
	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<Book> getBookById(@PathVariable int id) {
		
		Optional<Book> book = booksRepository.findById(id);
		
		if (book.isPresent())
		{
			return ResponseEntity.ok(book.get());
		}
		else
		{
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Book> createBook(@RequestBody Book newBook) {
	    return ResponseEntity.ok(booksRepository.save(newBook));
	}
	
	@DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
		booksRepository.deleteById(id);
		return ResponseEntity.ok("Successfuly deleted.");
		
    }
	
	@PutMapping(path="/{id}", produces = "application/json")
	  public ResponseEntity<Book> updateBook(@RequestBody Book newBook, @PathVariable int id) {
		
	    Optional<Book> b =  booksRepository.findById(id);
	    
	    if (b.isPresent())
	    {
	    	Book book = b.get();
	    	book.setNumPages(newBook.getNumPages());
	    	book.setTitle(newBook.getTitle());
	    	book.setPublicationYear(newBook.getPublicationYear());
	    	book.setPublisher(newBook.getPublisher());
	    	
	    	return ResponseEntity.ok(booksRepository.save(book));
	    }
	    else
	    {
	    	return ResponseEntity.notFound().build();
	    }
	  }

}
