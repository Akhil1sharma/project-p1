package com.librerymanagement.util;

public class IDGenerator {
	private static final int BASE_COUNTER = 1000;
	private static int bookCounter = BASE_COUNTER;
	private static int userCounter = BASE_COUNTER;
	private static int rentalCounter = BASE_COUNTER;
	private static boolean initialized = false;

	public static String generateBookId() {
		initializeCountersIfNeeded();
		bookCounter++;
		return "B" + bookCounter;
	}

	public static String generateUserId() {
		initializeCountersIfNeeded();
		userCounter++;
		return "U" + userCounter;
	}

	public static String generateRentalId() {
		initializeCountersIfNeeded();
		rentalCounter++;
		return "R" + rentalCounter;
	}

	private static synchronized void initializeCountersIfNeeded() {
		if (initialized) {
			return;
		}

		bookCounter = Math.max(bookCounter, readMaxId("data/books.txt", 'B'));
		userCounter = Math.max(userCounter, readMaxId("data/users.txt", 'U'));
		rentalCounter = Math.max(rentalCounter, readMaxId("data/rentals.txt", 'R'));
		initialized = true;
	}

	private static int readMaxId(String filePath, char prefix) {
		int max = BASE_COUNTER;
		for (String line : FileUtil.readFile(filePath)) {
			String trimmed = line.trim();
			if (trimmed.isEmpty()) {
				continue;
			}
			String[] parts = trimmed.split(",", 2);
			if (parts.length == 0) {
				continue;
			}
			String id = parts[0].trim();
			if (id.length() < 2 || id.charAt(0) != prefix) {
				continue;
			}
			String numberPart = id.substring(1);
			try {
				int value = Integer.parseInt(numberPart);
				if (value > max) {
					max = value;
				}
			} catch (NumberFormatException e) {
				// Ignore malformed IDs.
			}
		}
		return max;
	}
}
