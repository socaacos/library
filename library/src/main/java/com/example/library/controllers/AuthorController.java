
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
@RequestMapping("author")
public class AuthorController {

	@GetMapping(path="/{id}", produces = "application/json")
	public String getAuthor(@PathVariable int id) {
		return "Get author "+id;
	}
	
	@PostMapping()
	public String postAuthor() {
		return "Post author.";
	}

}
