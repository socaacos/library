package com.example.library.dtos;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.example.library.entities.Book;

@Mapper
public interface IBookMapper {

	IBookMapper bookMapper = Mappers.getMapper(IBookMapper.class);
	@Mappings({
	@Mapping(source = "book.id",target = "id"),
	@Mapping(source = "book.title",target = "title"),
	@Mapping(source = "book.publisher",target = "publisher"),
	@Mapping(source = "book.publicationYear",target = "publicationYear"),
	@Mapping(source = "book.numPages",target = "numPages") })
	BookDto bookToBookDto(Book book);
	
	@Mapping(source = "bookDto.id",target = "id")
	@Mapping(source = "bookDto.title",target = "title")
	@Mapping(source = "bookDto.publisher",target = "publisher")
	@Mapping(source = "bookDto.publicationYear",target = "publicationYear")
	@Mapping(source = "bookDto.numPages",target = "numPages")
	Book bookDtoToBook(BookDto bookDto);
}
