package com.example.library.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.dtos.CityDto;
import com.example.library.entities.City;
import com.example.library.exceptions.BookNotFoundException;
import com.example.library.repositories.CityRepository;
import com.example.library.repositories.LibraryRepository;

@Service
public class CityService {
	
	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	LibraryRepository libraryRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public List<CityDto> getAll()
	{
		List<City> cities = (List<City>) cityRepository.findAll();
		List<CityDto> cityDtos = new ArrayList<CityDto>();
		for (City city : cities) {
			cityDtos.add(modelMapper.map(city, CityDto.class));
		}
		return cityDtos;
	}

	public CityDto getById(int id)
	{
		Optional<City> city = cityRepository.findById(id);
		
		if(city.isPresent())
		{
			CityDto cityDto = modelMapper.map(city.get(), CityDto.class);
			return cityDto;
		}
		else
			throw new BookNotFoundException();	
	}
	
	public List<CityDto> getByName(String cityName)
	{
		List<City> cities = (List<City>) cityRepository.searchByName(cityName);
		List<CityDto> cityDtos = new ArrayList<CityDto>();
		for (City city : cities) {
			cityDtos.add(modelMapper.map(city, CityDto.class));
		}
		return cityDtos;
	}
	
	public void delete(int id)
	{
		CityDto cityDto = getById(id);
		City city = modelMapper.map(cityDto, City.class);
		if(city != null) {
			libraryRepository.deleteByCity(city);
			cityRepository.delete(city);
		}
			
		else
			System.out.println("Nema grada!");
	}
	
	public CityDto create(CityDto newCityDto)
	{
		City city = modelMapper.map(newCityDto, City.class);
		City newCity= cityRepository.save(city);
	    return modelMapper.map(newCity, CityDto.class);
	}
	
	public CityDto update(int id, CityDto newCityDto)
	{
		CityDto cityDto = getById(id);
		cityDto.setCityName(newCityDto.getCityName());
		City city = modelMapper.map(cityDto, City.class);
		cityRepository.save(city);
		return modelMapper.map(city, CityDto.class);
	}
}
