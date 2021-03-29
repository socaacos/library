package com.example.library.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.library.entities.Person;
public interface PersonRepository extends CrudRepository<Person, Integer> {
	@Modifying
	@Query("select name from Person p where p.name like %:personName%")
	String findByName(String personName);

}
