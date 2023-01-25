package com.blogger.tech.exception;

public class UnauthorizedException extends RuntimeException{
	
	public UnauthorizedException(String resourceName,String propertyName, String propertyValue) {
		super(String.format("User is unauthorized to  access %1$s with %2$s %3$s", resourceName,propertyName,propertyValue));
	}

}
