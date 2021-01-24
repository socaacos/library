package com.example.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.library.entities.Book;

@EnableTransactionManagement
public interface BooksRepository extends CrudRepository<Book, Integer> {
	
	public List<Book> findByPublisherOrTitle(String publisher, String title);
	
	@Query("select b from Book b where b.title like '%'|| :title ||'%'")
	public List<Book> searchByTitle(@Param("title") String title);

}