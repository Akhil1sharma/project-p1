package com.librerymanagement.mapper;

import com.librerymanagement.model.Rental;

public final class RentalCsvMapper {
	private RentalCsvMapper() {
	}

	public static String toCsv(Rental rental) {
		String safeReturnDate = rental.getReturnDate() == null ? "NULL" : rental.getReturnDate();
		return rental.getRentalId() + "," + rental.getBookId() + "," + rental.getUserId() + ","
			+ rental.getIssueDate() + "," + safeReturnDate + "," + rental.getStatus();
	}

	public static Rental fromCsv(String csv) {
		String[] parts = csv.split(",");
		Rental rental = new Rental(parts[0], parts[1], parts[2], parts[3]);
		String returnDate = "NULL".equals(parts[4]) ? null : parts[4];
		rental.applyStoredState(returnDate, parts[5]);
		return rental;
	}
}
