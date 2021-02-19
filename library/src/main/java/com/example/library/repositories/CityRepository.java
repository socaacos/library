package com.example.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.library.entities.City;

public interface CityRepository extends CrudRepository<City, Integer> {
	
	@Query("select c from City c where c.cityName like '%'|| :cityName ||'%'")
	public List<City> searchByName(@Param("cityName") String cityName);

}
