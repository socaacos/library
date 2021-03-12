package com.example.library.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.dtos.LibraryDto;
import com.example.library.entities.Library;
import com.example.library.exceptions.BookNotFoundException;
import com.example.library.repositories.LibraryRepository;

@Service

public class LibraryService {

	@Autowired
	LibraryRepository libraryRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public List<LibraryDto> getAll()
	{
		List<Library> libraries = (List<Library>) libraryRepository.findAll();
		List<LibraryDto> libraryDtos = new ArrayList<LibraryDto>();
		for (Library library : libraries) {
			libraryDtos.add(modelMapper.map(library, LibraryDto.class));
		}
		return libraryDtos;	
	}
	
	public LibraryDto getById(int id)
	{
		Optional<Library> library = libraryRepository.findById(id);
		
		if(library.isPresent())
		{
			LibraryDto libraryDto = modelMapper.map(library.get(), LibraryDto.class);

			return libraryDto;
		}
		else
			throw new BookNotFoundException();	
	}
	
	public List<LibraryDto> getByName(String libraryName)
	{
		List<Library> libraries = (List<Library>) libraryRepository.searchByName(libraryName);
		List<LibraryDto> libraryDtos = new ArrayList<LibraryDto>();
		for (Library library : libraries) {
			libraryDtos.add(modelMapper.map(library, LibraryDto.class));
		}
		return libraryDtos;
	}
	
	public LibraryDto create(LibraryDto newLibraryDto)
	{
		Library library = modelMapper.map(newLibraryDto, Library.class);
		Library newLibrary= libraryRepository.save(library);
	    return modelMapper.map(newLibrary, LibraryDto.class);
	}
	
	public void delete(int id)
	{
		LibraryDto libraryDto = getById(id);
		Library library = modelMapper.map(libraryDto, Library.class);
		if(library != null)
			libraryRepository.delete(library);
		else
			System.out.println("Nema biblioteke");
	}
	
	public LibraryDto update(int id, LibraryDto newLibraryDto)
	{
		LibraryDto libraryDto = getById(id);
		libraryDto.setLibraryName(newLibraryDto.getLibraryName());
		libraryDto.setCity(newLibraryDto.getCity());
		libraryDto.setAddress(newLibraryDto.getAddress());
		Library library = modelMapper.map(libraryDto, Library.class);
		libraryRepository.save(library);
		return modelMapper.map(library, LibraryDto.class);
	}
}
