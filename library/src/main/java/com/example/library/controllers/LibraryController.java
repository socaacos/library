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
import com.example.library.dtos.LibraryDto;
import com.example.library.entities.City;
import com.example.library.entities.Library;
import com.example.library.services.LibraryService;

@RestController
@RequestMapping("libraries")

public class LibraryController {
	
	@Autowired
	LibraryService libraryService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@GetMapping()
	@ResponseBody
	public List<LibraryDto> getAll()
	{
		List<Library> libraries = libraryService.getAll();
		List<LibraryDto> libraryDtos = new ArrayList<LibraryDto>();
		for (Library library : libraries) {
			libraryDtos.add(modelMapper.map(library, LibraryDto.class));
		}
						
			return libraryDtos;	
	}
	
	@GetMapping("/search")
	@ResponseBody
	public List<LibraryDto> searchLibraries(@RequestParam String libraryName) {
		List<Library> libraries=  libraryService.getByName(libraryName);
		
		List<LibraryDto> libraryDtos = new ArrayList<LibraryDto>();
		for (Library library: libraries) {
			libraryDtos.add(modelMapper.map(library, LibraryDto.class));
		}
					
		return libraryDtos;
	}
	
	@GetMapping(path = "/{id}", produces = "application/json")
	@ResponseBody
	public LibraryDto getById(@PathVariable int id)
	{
		Library library= libraryService.getById(id);
		LibraryDto libraryDto = modelMapper.map(library, LibraryDto.class);
		return libraryDto;
	}
	
	@PostMapping
	@ResponseBody
	public LibraryDto createLibrary(@RequestBody LibraryDto newLibraryDto) {
		Library library = modelMapper.map(newLibraryDto, Library.class);
		Library newLibrary= libraryService.create(library);
	    return modelMapper.map(newLibrary, LibraryDto.class);
	}
	
	@DeleteMapping(path = "/{id}", produces = "application/json")
	@ResponseBody
    public String deleteCity(@PathVariable int id) {
		libraryService.delete(id);
		return "Successfuly deleted.";
		
    }	
	
	@PutMapping(path="/{id}", produces = "application/json")
	@ResponseBody
	public LibraryDto updateLibrary(@PathVariable int id, @RequestBody LibraryDto newLibraryDto) {
		Library library = modelMapper.map(newLibraryDto, Library.class);		
		Library newLibrary = libraryService.update(id, library);		
		return modelMapper.map(newLibrary, LibraryDto.class);	
	    
	}

}
