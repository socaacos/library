package com.example.library.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	CustomUserService userService;
	
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
      String username = authentication.getName();
      String password = authentication.getCredentials().toString();
      System.out.println(password);
      UserDetails newUserDetails = userService.loadUserByUsername(username);
      
      if (newUserDetails == null) {
			throw new BadCredentialsException("Authentication failed");
		}
      if (newUserDetails.getUsername().equals(username)) {
            return new UsernamePasswordAuthenticationToken(username, password, newUserDetails.getAuthorities());
       } else {
            throw new BadCredentialsException("Authentication failed");
       }
    }

    @Override
    public boolean supports(Class<?>aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
