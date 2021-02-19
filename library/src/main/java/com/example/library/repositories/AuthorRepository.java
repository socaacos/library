package com.example.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.library.entities.Author;
import com.example.library.entities.Book;

public interface AuthorRepository extends CrudRepository<Author, Integer> {
	
	@Query("select a from Author a where a.name like '%'|| :name ||'%'")
	public List<Author> searchByName(@Param("name") String name);

}