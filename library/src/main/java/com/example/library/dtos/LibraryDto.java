package com.example.library.dtos;

import com.example.library.entities.City;

import lombok.Data;

@Data
public class LibraryDto {
	
	private Integer id;
	private String libraryName;
	private CityDto city;
	private String address;

}
