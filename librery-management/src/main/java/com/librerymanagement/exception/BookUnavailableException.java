package com.librerymanagement.exception;

public class BookUnavailableException extends LibraryException {
	public BookUnavailableException(String message) {
		super(message);
	}

	public BookUnavailableException(String message, Throwable cause) {
		super(message, cause);
	}
}
