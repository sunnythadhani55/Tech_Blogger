package com.blogger.tech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ResourceAlreadyExists extends RuntimeException{
	public ResourceAlreadyExists(String resourseName) {
		super(String.format("%1$s already exists, can't create duplicate entry for the same!",resourseName));
	}
}
