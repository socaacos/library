package com.example.library.dtos;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.example.library.entities.Book;

@Mapper
public interface IBookMapper {

	
	BookDto bookToBookDto(Book book);
	
	
	Book bookDtoToBook(BookDto bookDto);
}
