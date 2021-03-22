package com.example.library.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.library.dtos.RoleDto;
import com.example.library.dtos.UserDto;

@Service()
public class CustomUserService implements UserDetailsService{

	@Autowired
	UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username.trim().isEmpty()) {
			throw new UsernameNotFoundException("username is empty");
		}
		UserDto user = userService.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("User " + username + " not found");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user));
	
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(UserDto user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		RoleDto role = user.getRole();
		authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		return authorities;
	}
	
	

}
