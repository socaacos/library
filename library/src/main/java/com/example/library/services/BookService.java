package com.example.library.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.library.dtos.AuthorDto;
import com.example.library.dtos.BookDto;
import com.example.library.entities.Author;
import com.example.library.entities.Book;
import com.example.library.exceptions.BookNotFoundException;
import com.example.library.repositories.BookRepository;

@Service
@RefreshScope


public class BookService{
	@Value("${pageSize}")
	public  Integer  pageSize;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public List<BookDto> findPaginated(int pageNo) {
		  Pageable paging = PageRequest.of(pageNo, pageSize);
	      Page<Book> pagedResult = bookRepository.findAll(paging);
	      List<Book> books = pagedResult.toList();
	      List<BookDto> bookDtos = new ArrayList<BookDto>();
			for (Book book : books) {
				bookDtos.add(modelMapper.map(book, BookDto.class));
			}
			return bookDtos;
	}
	
	
	public BookDto getById(int id)
	{
		Optional<Book> book = bookRepository.findById(id);
		
		if (book.isPresent())
		{
			BookDto bookDto = modelMapper.map(book.get(), BookDto.class);
			return bookDto;
		}
		else
			throw new BookNotFoundException();
	}
	
	public List<BookDto> getByPublisherOrTitle(AuthorDto authorDto, String title, Integer page)
	{
		Pageable paging = PageRequest.of(page, pageSize);
		Author author = modelMapper.map(authorDto, Author.class);
		List<Book> books = (List<Book>) bookRepository.findByAuthorOrTitle(author, title, paging);
		List<BookDto> bookDtos = new ArrayList<BookDto>();
		for (Book book : books) {
			bookDtos.add(modelMapper.map(book, BookDto.class));
		}
		return bookDtos;
	}
	
	public List<BookDto> searchByTitle(String title, int page)
	{
		Pageable paging = PageRequest.of(page, pageSize);

		List<Book> books = (List<Book>) bookRepository.searchByTitle(title, paging);
		List<BookDto> bookDtos = new ArrayList<BookDto>();
		for (Book book : books) {
			bookDtos.add(modelMapper.map(book, BookDto.class));
		}
		return bookDtos;
	}
	
	public BookDto create(BookDto newBookDto)
	{
		Book book = modelMapper.map(newBookDto, Book.class);
		Book newBook = bookRepository.save(book);
	    return modelMapper.map(newBook, BookDto.class);
	}
	
	public void delete(int id)
	{
		BookDto bookDto = getById(id);
		Book book = modelMapper.map(bookDto, Book.class);
		if(book != null)
			bookRepository.delete(book);
		else
			System.out.println("Nema knjige");
	}
	
	
	public BookDto update(int id, BookDto newBookDto)
	{
		BookDto bookDto = getById(id);
		bookDto.setNumPages(newBookDto.getNumPages());
		bookDto.setPublicationYear(newBookDto.getPublicationYear());
		bookDto.setLibraries(newBookDto.getLibraries());
		bookDto.setAuthor(newBookDto.getAuthor());
		bookDto.setTitle(newBookDto.getTitle());
		Book book = modelMapper.map(bookDto, Book.class);
		bookRepository.save(book);
		return modelMapper.map(book, BookDto.class);
		
	}

	

}
