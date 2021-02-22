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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data

public class Author {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	private Integer id;
	
	@Column
	private String name;
	
	/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="author_id")
	private Collection<Book> books = new ArrayList<Book>(); */
}
