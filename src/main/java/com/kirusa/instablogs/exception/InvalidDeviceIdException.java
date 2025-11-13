package com.kirusa.instablogs.exception;

public class InvalidDeviceIdException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidDeviceIdException(String message) {
		super(message);
	}
}
