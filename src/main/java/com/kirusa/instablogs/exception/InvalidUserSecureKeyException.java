package com.kirusa.instablogs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a provided user secure key is missing/invalid. Runtime exception
 * so it can be thrown from service layers without mandatory try/catch.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidUserSecureKeyException extends RuntimeException {

	public InvalidUserSecureKeyException() {
		super();
	}

	public InvalidUserSecureKeyException(String message) {
		super(message);
	}

	public InvalidUserSecureKeyException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidUserSecureKeyException(Throwable cause) {
		super(cause);
	}
}
