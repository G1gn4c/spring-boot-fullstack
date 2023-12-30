package com.gianluca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CustomerNoFieldChangeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3088971483429074225L;

	public CustomerNoFieldChangeException(String message) {
		super(message);
	}

}
