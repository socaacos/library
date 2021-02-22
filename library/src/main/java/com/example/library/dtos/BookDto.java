package com.example.library.dtos;

import java.util.ArrayList;
import java.util.Collection;

import com.example.library.entities.Author;
import com.example.library.entities.Library;

import lombok.Data;

@Data
public class BookDto 
{
	private Integer id;
	private String title;
	private Author author;
	private int publicationYear;
	private int numPages;
	private Collection<Library> libraries = new ArrayList<Library>();
}
