package com.gianluca.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class DefaultExceptionHandler {

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ApiError> handleException(CustomerNotFoundException customerNotFoundException,
			HttpServletRequest httpServletRequest) {
		ApiError apiError = new ApiError(httpServletRequest.getRequestURI(), customerNotFoundException.getMessage(),
				HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
		return new ResponseEntity<ApiError>(apiError, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InsufficientAuthenticationException.class)
	public ResponseEntity<ApiError> handleException(
			InsufficientAuthenticationException insufficientAuthenticationException,
			HttpServletRequest httpServletRequest) {
		ApiError apiError = new ApiError(httpServletRequest.getRequestURI(),
				insufficientAuthenticationException.getMessage(), HttpStatus.FORBIDDEN.value(), LocalDateTime.now());
		return new ResponseEntity<ApiError>(apiError, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiError> handleException(BadCredentialsException badCredentialsException,
			HttpServletRequest httpServletRequest) {
		ApiError apiError = new ApiError(httpServletRequest.getRequestURI(), badCredentialsException.getMessage(),
				HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now());
		return new ResponseEntity<ApiError>(apiError, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleException(Exception exception, HttpServletRequest httpServletRequest) {
		ApiError apiError = new ApiError(httpServletRequest.getRequestURI(), exception.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
		return new ResponseEntity<ApiError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
