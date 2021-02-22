package com.example.library.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.dtos.CityDto;
import com.example.library.entities.City;
import com.example.library.services.CityService;

@RestController
@RequestMapping("cities")
public class CityController {
	
	@Autowired
	CityService cityService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@GetMapping()
	@ResponseBody
	public List<CityDto> getAll()
	{
		List<City> cities = cityService.getAll();
		List<CityDto> cityDtos = new ArrayList<CityDto>();
		for (City city : cities) {
			cityDtos.add(modelMapper.map(city, CityDto.class));
		}
						
			return cityDtos;	
	}
	
	@GetMapping("/search")
	@ResponseBody
	public List<CityDto> searchCities(@RequestParam String cityName) {
		List<City> cities=  cityService.getByName(cityName);
		
		List<CityDto> cityDtos = new ArrayList<CityDto>();
		for (City city: cities) {
			cityDtos.add(modelMapper.map(city, CityDto.class));
		}
					
		return cityDtos;
	}
	
	@GetMapping(path = "/{id}", produces = "application/json")
	@ResponseBody
	public CityDto getById(@PathVariable int id)
	{
		City city = cityService.getById(id);
		CityDto cityDto = modelMapper.map(city, CityDto.class);
		return cityDto;
	}
	
	@PostMapping
	@ResponseBody
	public CityDto createCity(@RequestBody CityDto newCityDto) {
		City city = modelMapper.map(newCityDto, City.class);
		City newCity= cityService.create(city);
	    return modelMapper.map(newCity, CityDto.class);
	}
	
	@DeleteMapping(path = "/{id}", produces = "application/json")
	@ResponseBody
    public String deleteCity(@PathVariable int id) {
		cityService.delete(id);
		return "Successfuly deleted.";
		
    }	
	
	@PutMapping(path="/{id}", produces = "application/json")
	@ResponseBody
	public CityDto updateCity(@PathVariable int id, @RequestBody CityDto newCityDto) {
		City city = modelMapper.map(newCityDto, City.class);		
		City newCity = cityService.update(id, city);		
		return modelMapper.map(newCity, CityDto.class);	
	    
	}

}
