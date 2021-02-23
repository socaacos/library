package com.example.library.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

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
	
	@JsonIgnore
	@ManyToMany( cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	Collection<Library> libraries = new ArrayList<Library>();
	
	@Column(name = "publication_year")
	private int publicationYear;
	
	@Column(name = "num_pages")
	private int numPages;	
	
}
