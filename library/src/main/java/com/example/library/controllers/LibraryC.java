package com.example.library.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.openapitools.api.LibrariesApi;
import org.openapitools.model.City;
import org.openapitools.model.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.example.library.dtos.CityDto;
import com.example.library.dtos.LibraryDto;
import com.example.library.services.LibraryService;

@Controller

public class LibraryC implements LibrariesApi {
	
	@Autowired
	LibraryService libraryService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public ResponseEntity<List<Library>> getAll() {
		List<LibraryDto> libraries = libraryService.getAll();
		List<Library> librariesApi = new ArrayList<Library>();
		for (LibraryDto library  : libraries) {
			librariesApi.add(modelMapper.map(library, Library.class));			
		}
		return new ResponseEntity<List<Library>>(librariesApi, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Library> getById(Integer id) {
		LibraryDto libraryDto = libraryService.getById(id);
		Library libraryApi = modelMapper.map(libraryDto, Library.class);
		return new ResponseEntity<Library>(libraryApi, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<List<Library>> searchLibraries(String libraryName) {
		List<LibraryDto> libraries = libraryService.getByName(libraryName);
		List<Library> librariesApi = new ArrayList<Library>();
		for (LibraryDto library : libraries) {
			librariesApi.add(modelMapper.map(library, Library.class));			
		}
		return new ResponseEntity<List<Library>>(librariesApi, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Library> createLibrary(Library library) {
		LibraryDto libraryDto = modelMapper.map(library, LibraryDto.class);
		LibraryDto newLibrary = libraryService.create(libraryDto);
		Library libraryApi = modelMapper.map(newLibrary, Library.class);
		return new ResponseEntity<Library>(libraryApi, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<String> deleteCity(Integer id) {
		libraryService.delete(id);
		return new ResponseEntity<String>("Deleted", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Library> updateLibrary(Integer id, Library library) {
		LibraryDto libraryDto = modelMapper.map(library, LibraryDto.class);
		LibraryDto newLibrary = libraryService.update(id, libraryDto);
		Library libraryApi = modelMapper.map(newLibrary, Library.class);
		return new ResponseEntity<Library>(libraryApi, HttpStatus.OK);
	}

}
