package com.example.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.library.entities.Author;
import com.example.library.entities.Library;

public interface LibraryRepository extends CrudRepository<Library, Integer> {
	
	@Query("select l from Library l where l.libraryName like '%'|| :libraryName ||'%'")
	public List<Library> searchByName(@Param("libraryName") String libraryName);

}
