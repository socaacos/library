package com.example.library.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@Getter @Setter
public class Book {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column
	private String title;
	
	@Column
	private String publisher;
	
	@Column(name = "publication_year")
	private int publicationYear;
	
	@Column(name = "num_pages")
	private int numPages;	
	
}
