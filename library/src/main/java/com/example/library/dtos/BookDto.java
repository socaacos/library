package com.example.library.dtos;

import lombok.Data;

@Data
public class BookDto 
{
	private Integer id;
	private String title;
	private String publisher;
	private int publicationYear;
	private int numPages;
}
