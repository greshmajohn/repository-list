package com.list.repositories.exception;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import org.springframework.web.client.RestClientException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.ConstraintViolationException;
/*
 * author=greshma.john
 * 
 * ControllerAdvice class to customize exceptions
 */

@RestControllerAdvice
public class RepositoryGlobalExceptionHandling {

	/*
	 * Constraint violation handling invalid inputs . eg: pageSize=200, maximum page
	 * isze expected is 100
	 */

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ExceptionResponseDao> handleConstraintViolationException(ConstraintViolationException e) {

		return new ResponseEntity<>(
				new ExceptionResponseDao("ConstraintViolationException", e.getMessage(), HttpStatus.BAD_REQUEST.name()),
				HttpStatus.BAD_REQUEST);

	}

	/*
	 * Invalid input datatype of format. eg:passing wrong format for date field
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ExceptionResponseDao> handleMethodArgumentMismatchException(
			MethodArgumentTypeMismatchException e) {

		String message = String.format("'%s' should be a valid '%s' and the input '%s' provided is an invalid .",
				e.getName(), e.getRequiredType().getSimpleName(), e.getValue());

		return new ResponseEntity<>(
				new ExceptionResponseDao("MethodArgumentTypeMismatchException", message, HttpStatus.BAD_REQUEST.name()),
				HttpStatus.BAD_REQUEST);

	}

	/*
	 * Exception while fetching Rest API response
	 */

	@ExceptionHandler(RestClientException.class)
	public ResponseEntity<ExceptionResponseDao> handleRestClientException(RestClientException e) {
		String title = "RestClientException";
		String msg = e.getMessage();

		if (e instanceof HttpClientErrorException ex) {

			title = "HttpClientErrorException- " + ex.getStatusText();
			msg = formatExceptionMessage(ex.getResponseBodyAsString());
		}

		return new ResponseEntity<>(new ExceptionResponseDao(title, msg, HttpStatus.BAD_REQUEST.name()),
				HttpStatus.BAD_REQUEST);
	}

	private String formatExceptionMessage(String message) {
		JSONObject responseJson = new JSONObject(message);
		return "Could not get result from git api. " + responseJson.getString("message");
	}

}
