package com.example.library.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.library.controllers.BookController;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice(assignableTypes = {BookController.class})
@Slf4j
public class BookExceptionHandler {
	
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found")
	@ExceptionHandler(value = BookNotFoundException.class)
	public void  notFoundHandler(HttpServletRequest req, Exception e)
	{
		log.error(req.getRequestURL()+"  - book not found.");
		
	}


}
