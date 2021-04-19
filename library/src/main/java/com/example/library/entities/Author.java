package com.example.library.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data

public class Author {
	public Author() {}
	
	public Author(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Author(String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	private Integer id;
	
	@Column
	private String name;
	
	/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="author_id")
	private Collection<Book> books = new ArrayList<Book>(); */
}
