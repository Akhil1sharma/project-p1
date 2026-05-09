package com.librerymanagement.exception;

public class InvalidOperationException extends LibraryException {
	public InvalidOperationException(String message) {
		super(message);
	}

	public InvalidOperationException(String message, Throwable cause) {
		super(message, cause);
	}
}
