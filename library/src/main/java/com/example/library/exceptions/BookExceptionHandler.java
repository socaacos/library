package com.example.library.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.library.controllers.BookController;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@ControllerAdvice

public class BookExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({BookNotFoundException.class})
	protected ResponseEntity<Object> handleBind(BookNotFoundException ex, WebRequest request) {
		log.error(ex.getMessage());
		BookError error= new BookError(HttpStatus.NOT_FOUND, "Book is not found");
	    return new ResponseEntity<Object>(error, new HttpHeaders(), error.getStatus());
	}
	

}
