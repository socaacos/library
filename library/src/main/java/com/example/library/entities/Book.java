package com.example.library.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data

public class Book {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	private Integer id;
	
	@Column
	private String title;
	
	
	@ManyToOne
	private Author author;
	
	@Column(name = "publication_year")
	private int publicationYear;
	
	@Column(name = "num_pages")
	private int numPages;	
	
}
