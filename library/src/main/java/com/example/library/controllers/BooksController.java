
package com.example.library.controllers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("book")
public class BooksController {

	@GetMapping(path="/{id}", produces = "application/json")
	public String getBook(@PathVariable int id) {
		return "Get book " + id;
	}
	
	@PostMapping()
	public String postBook() {
		return "Post book.";
	}

}
