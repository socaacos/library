package com.example.library.dtos;

import lombok.Data;

@Data
public class AuthorDto {
	public AuthorDto() {}
	
	
	public AuthorDto(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public AuthorDto(String name) {
	
		this.name = name;
	}
	private int id;
	private String name;

}
