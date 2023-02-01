package com.blogger.tech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String resourceName, String propertyName, String propertyValue) {
    super(String.format("%1$s does not exists for %2$s %3$s", resourceName, propertyName,
        propertyValue));
  }
}
