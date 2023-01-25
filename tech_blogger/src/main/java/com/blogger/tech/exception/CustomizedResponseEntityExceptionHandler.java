package com.blogger.tech.exception;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = "com.blogger.tech.controller")
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(),
                ex.getMessage(),request.getDescription(false));

        ex.printStackTrace();
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleResourceNotFoundException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(),
                ex.getMessage(),request.getDescription(false));
        ex.printStackTrace();
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<ErrorDetails> handleUnauthorizedException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(),
                ex.getMessage(),request.getDescription(false));
        ex.printStackTrace();
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(ResourceAlreadyExists.class)
    public final ResponseEntity<ErrorDetails> handleDuplicateRecordException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
        ex.printStackTrace();
        
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ErrorDetails> handleDataIntegrationViolationException(DataIntegrityViolationException ex, WebRequest request) {
        ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(),
                ex.getRootCause().getMessage(),request.getDescription(false));
        ex.printStackTrace();
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ErrorDetails> handleConstraintFoundException(ConstraintViolationException ex, WebRequest request) {
    	
    	  Set<ConstraintViolation<?>> violations=ex.getConstraintViolations();
    	  ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(),
          		"Total Errors are : "+violations.size()+" , First Error : "+violations.stream().findFirst().get().getMessage(),request.getDescription(false));

        ex.printStackTrace();
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {	
        ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(),
        		"Total Errors are : "+ex.getErrorCount()+" , First Error : "+ex.getFieldError().getDefaultMessage(),request.getDescription(false));

        ex.printStackTrace();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    
}
