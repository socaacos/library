package com.example.library.dtos;

import com.example.library.entities.City;

import lombok.Data;

@Data
public class LibraryDto {
	
	private Integer id;
	private String libraryName;
	private City city;
	private String address;

}
