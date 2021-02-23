package com.example.library.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Library {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String libraryName;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "libraries",  cascade = CascadeType.ALL)
	Collection<Book> books = new ArrayList<Book>();
	
	@OneToOne
	private City city;
	
	@Column
	private String address;
}
