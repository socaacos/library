package com.example.library.dtos;

import com.example.library.entities.Author;

import lombok.Data;

@Data
public class BookDto 
{
	private Integer id;
	private String title;
	private Author author;
	private int publicationYear;
	private int numPages;
}
