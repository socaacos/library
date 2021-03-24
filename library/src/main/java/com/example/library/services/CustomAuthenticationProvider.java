package com.example.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	CustomUserService userService;
	
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
      String username = authentication.getName();
      String password = authentication.getCredentials().toString();
      
      UserDetails newUserDetails = userService.loadUserByUsername(username);
      String newPassword = bCryptPasswordEncoder().encode(password);
      System.out.println(newPassword);
      if (newUserDetails == null) {
			throw new BadCredentialsException("Authentication failed");
		}
      if (newUserDetails.getUsername().equals(username)) {
    	  System.out.println(newUserDetails.getPassword());
    	  
            return new UsernamePasswordAuthenticationToken(username, password, newUserDetails.getAuthorities());
       } else {
            throw new BadCredentialsException("Authentication failed");
       }
    }
    

	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public boolean supports(Class<?>aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
