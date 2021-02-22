package com.example.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.library.entities.Author;
import com.example.library.entities.Book;
@Transactional

public interface BookRepository extends CrudRepository<Book, Integer> {
	
	public List<Book> findByAuthorOrTitle(Author author, String title);
	
	@Query("select b from Book b where b.title like '%'|| :title||'%'")
	public List<Book> searchByTitle(@Param("title") String title);
	
	@Modifying
	@Query("Delete FROM Book b WHERE b.author = :author")  
	public void deleteByAuthor(@Param("author") Author author);
	
}