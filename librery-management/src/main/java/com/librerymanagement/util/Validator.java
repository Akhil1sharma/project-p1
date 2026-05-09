package com.librerymanagement.util;

public class Validator {
	public static boolean isNotEmpty(String value) {
		return value != null && !value.trim().isEmpty();
	}

	public static boolean isValidEmail(String email) {
		return email != null && email.contains("@");
	}
}
