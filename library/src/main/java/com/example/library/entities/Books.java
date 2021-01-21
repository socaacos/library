package com.example.library.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Books {

	@Id
	@GeneratedValue
	private int id;
	
	@Column
	private String title;
	
	@Column
	private String publisher;
	
	@Column
	private int publicationYear;
	
	@Column
	private int numPages;
	
}
