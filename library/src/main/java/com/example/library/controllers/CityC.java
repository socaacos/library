package com.example.library.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.openapitools.api.CitiesApi;
import org.openapitools.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.example.library.dtos.CityDto;
import com.example.library.services.CityService;

@Controller

public class CityC implements CitiesApi {
	
	@Autowired
	CityService cityService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public ResponseEntity<List<City>> getAll1() {
		List<CityDto> cities = cityService.getAll();
		List<City> citiesApi = new ArrayList<City>();
		for (CityDto city : cities) {
			citiesApi.add(modelMapper.map(city, City.class));			
		}
		return new ResponseEntity<List<City>>(citiesApi, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<City> getById1(Integer id) {
		CityDto cityDto = cityService.getById(id);
		City cityApi = modelMapper.map(cityDto, City.class);
		return new ResponseEntity<City>(cityApi, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<List<City>> searchCities(String cityName) {
		List<CityDto> cities = cityService.getByName(cityName);
		List<City> citiesApi = new ArrayList<City>();
		for (CityDto city : cities) {
			citiesApi.add(modelMapper.map(city, City.class));			
		}
		return new ResponseEntity<List<City>>(citiesApi, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<City> createCity(City city) {
		CityDto cityDto = modelMapper.map(city, CityDto.class);
		CityDto newCity = cityService.create(cityDto);
		City cityApi = modelMapper.map(newCity, City.class);
		return new ResponseEntity<City>(cityApi, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<String> deleteCity1(Integer id) {
		cityService.delete(id);
		return new ResponseEntity<String>("Deleted", HttpStatus.OK);
			
	}
	
	@Override
	public ResponseEntity<City> updateCity(Integer id, City city) {
		CityDto cityDto = modelMapper.map(city, CityDto.class);
		CityDto newCity = cityService.update(id, cityDto);
		City cityApi = modelMapper.map(newCity, City.class);
		return new ResponseEntity<City>(cityApi, HttpStatus.OK);
	}

}
