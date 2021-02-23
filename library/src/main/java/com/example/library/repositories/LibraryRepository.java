package com.example.library.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.library.entities.City;
import com.example.library.entities.Library;


@Transactional
public interface LibraryRepository extends CrudRepository<Library, Integer> {
	
	@Query("select l from Library l where l.libraryName like '%'|| :libraryName ||'%'")
	public List<Library> searchByName(@Param("libraryName") String libraryName);

	public void deleteByCity(City city);
}
