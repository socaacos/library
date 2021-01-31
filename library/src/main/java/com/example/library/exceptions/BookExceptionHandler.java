package com.example.library.exceptions;

import java.net.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.example.library.controllers.BookController;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice(assignableTypes = {BookController.class})
@Slf4j
public class BookExceptionHandler {
	
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found")
	@ExceptionHandler(value = NotFoundException.class)
	public void  notFoundHandler(HttpServletRequest req, Exception e)
	{
		log.error(req.getRequestURL()+" did not found what was loking for.");
		
	}


}
