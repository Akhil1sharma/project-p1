package com.librerymanagement.model;

public class Rental {
	private String rentalId;
	private String bookId;
	private String userId;
	private String issueDate;
	private String returnDate;
	private String status;

	public Rental(String rentalId, String bookId, String userId, String issueDate) {
		this.rentalId = rentalId;
		this.bookId = bookId;
		this.userId = userId;
		this.issueDate = issueDate;
		this.returnDate = null;
		this.status = "ACTIVE";
	}

	public String getRentalId() {
		return rentalId;
	}

	public String getBookId() {
		return bookId;
	}

	public String getUserId() {
		return userId;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public String getStatus() {
		return status;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
		this.status = "RETURNED";
	}

	public String toCSV() {
		String safeReturnDate = returnDate == null ? "NULL" : returnDate;
		return rentalId + "," + bookId + "," + userId + "," + issueDate + "," + safeReturnDate + "," + status;
	}

	public static Rental fromCSV(String csv) {
		String[] parts = csv.split(",");
		Rental rental = new Rental(parts[0], parts[1], parts[2], parts[3]);
		rental.returnDate = "NULL".equals(parts[4]) ? null : parts[4];
		rental.status = parts[5];
		return rental;
	}
}
