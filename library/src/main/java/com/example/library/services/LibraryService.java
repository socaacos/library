package com.example.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.entities.Library;
import com.example.library.exceptions.BookNotFoundException;
import com.example.library.repositories.LibraryRepository;

@Service

public class LibraryService {

	@Autowired
	LibraryRepository libraryRepository;
	
	public List<Library> getAll()
	{
		return (List<Library>)libraryRepository.findAll();
	}
	
	public Library getById(int id)
	{
		Optional<Library> library = libraryRepository.findById(id);
		if(library.isPresent())
			return library.get();
		else
			throw new BookNotFoundException();
	}
	
	public List<Library> getByName(String libraryName)
	{
		return libraryRepository.searchByName(libraryName);
	}
	
	public Library create(Library newLibrary)
	{
		return libraryRepository.save(newLibrary);
	}
	
	public void delete(int id)
	{
		Library library = getById(id);
		if(library != null)
			libraryRepository.delete(library);
		else
			System.out.println("Nema biblioteke");
	}
	
	public Library update(int id, Library newLibrary)
	{
		Library library = getById(id);
		library.setLibraryName(newLibrary.getLibraryName());
		library.setCity(newLibrary.getCity());
		library.setAddress(newLibrary.getAddress());
		
		return libraryRepository.save(library);
	}
}
