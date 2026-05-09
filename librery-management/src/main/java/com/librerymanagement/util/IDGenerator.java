package com.librerymanagement.util;

public class IDGenerator {
	private static int bookCounter = 1000;
	private static int userCounter = 1000;
	private static int rentalCounter = 1000;

	public static String generateBookId() {
		bookCounter++;
		return "B" + bookCounter;
	}

	public static String generateUserId() {
		userCounter++;
		return "U" + userCounter;
	}

	public static String generateRentalId() {
		rentalCounter++;
		return "R" + rentalCounter;
	}
}
