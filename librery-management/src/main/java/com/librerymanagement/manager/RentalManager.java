package com.librerymanagement.manager;

import com.librerymanagement.exception.BookUnavailableException;
import com.librerymanagement.exception.InvalidOperationException;
import com.librerymanagement.exception.LibraryException;
import com.librerymanagement.model.Book;
import com.librerymanagement.model.Rental;
import com.librerymanagement.model.User;
import com.librerymanagement.util.FileUtil;
import com.librerymanagement.util.IDGenerator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentalManager {
	private static final String FILE_PATH = "data/rentals.txt";
	private final BookManager bookManager = new BookManager();
	private final UserManager userManager = new UserManager();

	public void issueBook(String bookId, String userId) throws LibraryException {
		Book book = bookManager.getBookById(bookId);
		User user = userManager.getUserById(userId);

		if (!book.isAvailable()) {
			throw new BookUnavailableException("Book is not available");
		}

		String rentalId = IDGenerator.generateRentalId();
		String issueDate = LocalDate.now().toString();
		Rental rental = new Rental(rentalId, bookId, userId, issueDate);

		bookManager.updateBookAvailability(bookId, false);
		FileUtil.appendToFile(FILE_PATH, rental.toCSV());
		System.out.println("Book issued successfully. Rental ID: " + rentalId);
	}

	public void returnBook(String rentalId) throws LibraryException {
		List<Rental> rentals = getAllRentals();
		Rental rentalToReturn = null;

		for (Rental rental : rentals) {
			if (rental.getRentalId().equals(rentalId)) {
				rentalToReturn = rental;
				break;
			}
		}

		if (rentalToReturn == null) {
			throw new InvalidOperationException("Rental not found: " + rentalId);
		}

		if ("RETURNED".equals(rentalToReturn.getStatus())) {
			throw new InvalidOperationException("Book already returned");
		}

		rentalToReturn.setReturnDate(LocalDate.now().toString());
		bookManager.updateBookAvailability(rentalToReturn.getBookId(), true);
		writeAllRentals(rentals);
		System.out.println("Book returned successfully");
	}

	public List<Rental> getRentalHistory(String userId) {
		List<Rental> history = new ArrayList<>();

		for (Rental rental : getAllRentals()) {
			if (rental.getUserId().equals(userId)) {
				history.add(rental);
			}
		}

		return history;
	}

	public List<Rental> getAllRentals() {
		List<Rental> rentals = new ArrayList<>();
		List<String> lines = FileUtil.readFile(FILE_PATH);

		for (String line : lines) {
			if (!line.trim().isEmpty()) {
				rentals.add(Rental.fromCSV(line));
			}
		}

		return rentals;
	}

	private void writeAllRentals(List<Rental> rentals) {
		List<String> csvLines = new ArrayList<>();
		for (Rental rental : rentals) {
			csvLines.add(rental.toCSV());
		}
		FileUtil.writeFile(FILE_PATH, csvLines);
	}
}
