package com.blogger.tech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class IllegalStatusException extends RuntimeException {
  public IllegalStatusException(String oldStatus, String newStatus) {
    super(String.format("Can not update status from %1$s to %2$s", oldStatus, newStatus));
  }
}
