package com.example.library.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.library.dtos.AuthorDto;
import com.example.library.entities.Author;
import com.example.library.repositories.AuthorRepository;

@SpringBootTest
class AuthorServiceTest {

	@Autowired
	AuthorService authorServicer;

	@Autowired
	ModelMapper modelMaper;

	@MockBean
	AuthorRepository authorRepo;

	@Test
	void getAllTest() {
		when(authorRepo.findAll())
				.thenReturn(Stream.of(new Author("Sofija"), new Author("Viktorija")).collect(Collectors.toList()));
		assertEquals(2, authorServicer.getAll().size(), "There was a mistake with getAll function.");
	}

	@Test
	void getByNameTest() {
		String name = "Sofija";
		when(authorRepo.searchByName(name))
				.thenReturn(Stream.of(new Author("Sofija"), new Author("Sofija")).collect(Collectors.toList()));
		List<AuthorDto> authorsDto = authorServicer.searchByName(name);
		assertEquals(2, authorsDto.size(), "There was a mistake with searchByName function.");
	}

	@Test
	void getByIdTest() {
		Optional<Author> a = Optional.of(new Author(1, "S"));
		int id = 1;
		when(authorRepo.findById(id)).thenReturn(a);
		AuthorDto author = modelMaper.map(a.get(), AuthorDto.class);
		AuthorDto authorDto = authorServicer.getById(id);
		assertEquals(author, authorDto, "There was a mistake with getById function.");
	}

	@Test
	void createTest() {
		Author author = new Author(1, "Sofija Jakovljevic");
		System.out.println(author);
		AuthorDto authorDto = modelMaper.map(author, AuthorDto.class);
		when(authorRepo.save(author)).thenReturn(author);
		AuthorDto newAuth = authorServicer.create(authorDto);
		Author a = modelMaper.map(newAuth, Author.class);
		assertEquals(author, a, "There was a mistake with create function.");
	}

	@Test
	void updateTest() {
		Author authorToPut = new Author("Sofija");
		AuthorDto authorToPutDto = modelMaper.map(authorToPut, AuthorDto.class);
		Author authorToSave = new Author(1, "Sofija");
		Optional<Author> authorToReturn = Optional.of(new Author(1, "Sofija"));
		AuthorDto authorToReturDto = modelMaper.map(authorToReturn.get(), AuthorDto.class);
		int id = 1;
		when(authorRepo.findById(id)).thenReturn(authorToReturn);
		when(authorRepo.save(authorToPut)).thenReturn(authorToSave);
		
		assertEquals(authorToReturDto, authorServicer.update(id, authorToPutDto));

	}

	@Test 
	 void deleteTest() { 
		Optional<Author> a = Optional.of(new Author(1, "S"));
		int id = 1;
		when(authorRepo.findById(id)).thenReturn(a);
		 assertEquals(true, authorServicer.delete(id),"There was a mistake with delete function.");
	 }

}
