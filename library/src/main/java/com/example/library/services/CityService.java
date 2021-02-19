package com.example.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.entities.Author;
import com.example.library.entities.City;
import com.example.library.exceptions.BookNotFoundException;
import com.example.library.repositories.CityRepository;

@Service
public class CityService {
	
	@Autowired
	CityRepository cityRepository;
	
	public List<City> getAll()
	{
		return (List<City>) cityRepository.findAll();
	}

	public City getById(int id)
	{
		Optional<City> city = cityRepository.findById(id);
		
		if(city.isPresent())
			return city.get();
		else
			throw new BookNotFoundException();	
	}
	
	public List<City> getByName(String cityName)
	{
		return cityRepository.searchByName(cityName);
	}
	
	public void delete(int id)
	{
		City city = getById(id);
		if(city != null)
			cityRepository.delete(city);
		else
			System.out.println("Nema grada!");
	}
	
	public City create(City newCity)
	{
		return cityRepository.save(newCity);
	}
	
	public City update(int id, City newCity)
	{
		City city = getById(id);
		city.setCityName(newCity.getCityName());
		return cityRepository.save(city);
	}
}
