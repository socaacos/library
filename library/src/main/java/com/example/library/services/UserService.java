package com.example.library.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.dtos.UserDto;
import com.example.library.entities.User;
import com.example.library.repositories.UserRepository;
@Service

public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public UserDto findByUsername(String username)
	{
		User user = userRepository.findByUsername(username);
		UserDto userDto = modelMapper.map(user, UserDto.class);
		return userDto;
	}

}
