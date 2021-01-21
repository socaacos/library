
package com.example.library.controllers;

import java.awt.print.Book;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.support.Repositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.entities.Books;
import com.example.library.repositories.BooksRepository;


@RestController
@RequestMapping("book")
public class BooksController {

	@GetMapping(path = "/{id}", produces = "application/json")
	public Books getBook(@PathVariable int id) {
		
		return null;			
	}

	@PostMapping()
	public String postBook() {
		return "Post book.";
	}

}
