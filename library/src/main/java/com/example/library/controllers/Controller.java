package com.example.library.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.entities.Person;
import com.example.library.repositories.PersonRepository;

@RestController
@RequestMapping("person")
public class Controller {
	@Autowired
	private PersonRepository personRepo;
	
	@PostMapping("person")
	public String createPerson(@RequestParam String name)
	{
		personRepo.save(new Person(name, "6.7"));
		return personRepo.findByName(name) + "Saved";
	}
	
	@GetMapping("person")
	public List<Person> getAll()
	{
		System.out.println("Bui");
		return (List<Person>) personRepo.findAll();
	}

}
