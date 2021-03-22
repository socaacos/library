package com.example.library.dtos;

import lombok.Data;

@Data
public class UserDto {
	
	private Integer id;
	private String username;
	private String password;
	private RoleDto role;

}
