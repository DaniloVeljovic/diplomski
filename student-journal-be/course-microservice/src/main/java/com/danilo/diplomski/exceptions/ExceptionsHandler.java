package com.danilo.diplomski.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.danilo.diplomski.models.DTO.ErrorMessage;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest req)
	{
		String errorMessageDesc = ex.getLocalizedMessage();
		
		if(errorMessageDesc == null) errorMessageDesc = ex.toString();
		
		ErrorMessage err = new ErrorMessage(new Date(), errorMessageDesc);
		
		return new ResponseEntity<>(err, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = { NullPointerException.class, IllegalArgumentException.class, IllegalIdException.class})
	public ResponseEntity<Object> handleNullPointerException(Exception ex, WebRequest req)
	{
		String errorMessageDesc = ex.getLocalizedMessage();
		
		if(errorMessageDesc == null) errorMessageDesc = ex.toString();
		
		ErrorMessage err = new ErrorMessage(new Date(), errorMessageDesc);
		
		return new ResponseEntity<>(err, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = { StudentNotEnroledException.class})
	public ResponseEntity<Object> handleStudentNotEnroled(Exception ex, WebRequest req)
	{
		String errorMessageDesc = ex.getLocalizedMessage();
		
		if(errorMessageDesc == null) errorMessageDesc = ex.toString();
		
		ErrorMessage err = new ErrorMessage(new Date(), errorMessageDesc);
		
		return new ResponseEntity<>(err, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
}
