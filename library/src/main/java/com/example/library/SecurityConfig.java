package com.example.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.library.services.CustomAuthenticationProvider;
import com.example.library.services.CustomUserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer   {
	
	@Autowired 
	CustomAuthenticationProvider authProvider;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.csrf().disable()
		 .authenticationProvider(authProvider)
		 .authorizeRequests()
		 .antMatchers(HttpMethod.GET).permitAll()
		 .and()
		 .authorizeRequests()
		 .antMatchers(HttpMethod.PUT).hasAuthority("user")
		 .and()
		 .authorizeRequests()
		 .antMatchers(HttpMethod.POST).hasAuthority("admin")
		 .and()
		 .authorizeRequests()
		 .antMatchers(HttpMethod.DELETE).hasAuthority("admin")
		 .anyRequest()
		 .authenticated()
		 .and()
		 .httpBasic();

	}
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }
	
	/*@Override
	protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
		authManagerBuilder.authenticationProvider(authProvider);
//		authManagerBuilder.inMemoryAuthentication()
//		.withUser("sofija")
//		.password("sofija")
//		.roles("admin");
	}*/
	
	
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
