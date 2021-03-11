package com.example.library.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;
@Data
public class BookError {

    private HttpStatus status;
    private String message;
    //private List<String> errors;

    public BookError(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
        //this.errors = errors;
    }

}