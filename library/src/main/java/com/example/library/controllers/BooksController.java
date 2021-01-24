
package com.example.library.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Book> getBooks(@RequestParam(required = false) String publisher, @RequestParam(required = false) String title) {
		
		if (publisher == null && title == null)
			return (List<Book>) booksRepository.findAll();
		return (List<Book>) booksRepository.findByPublisherOrTitle(publisher, title);
	}
	
	@GetMapping("/search")	
	public List<Book> searchBooks(@RequestParam String title) {
		return (List<Book>) booksRepository.searchByTitle(title);
	}
	
	@GetMapping(path = "/{id}", produces = "application/json")
	public Optional<Book> getBookById(@PathVariable int id) {
		
		return booksRepository.findById(id);			
	}
	
	@PostMapping
	  public Book createBook(@RequestBody Book newBook) {
	    return booksRepository.save(newBook);
	  }
	
	@DeleteMapping(path = "/{id}", produces = "application/json")
    public boolean deleteBook(@PathVariable int id) {
		try {
			booksRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
		
    }
	
	@PutMapping(path="/{id}", produces = "application/json")
	  public Optional<Object> updateBook(@RequestBody Book newBook, @PathVariable int id) {
		
	    return booksRepository.findById(id)
	      .map(book -> {
	        book.setTitle(newBook.getTitle());
	        book.setPublisher(newBook.getPublisher());
	        book.setPublicationYear(newBook.getPublicationYear());
	        book.setNumPages(newBook.getNumPages());
	        return booksRepository.save(book);
	      });
	  }

}
