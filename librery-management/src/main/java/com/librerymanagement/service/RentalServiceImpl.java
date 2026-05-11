package com.librerymanagement.service;

import com.librerymanagement.exception.BookUnavailableException;
import com.librerymanagement.exception.InvalidOperationException;
import com.librerymanagement.exception.LibraryException;
import com.librerymanagement.model.Book;
import com.librerymanagement.model.Rental;
import com.librerymanagement.repository.FileRentalRepository;
import com.librerymanagement.repository.RentalRepository;
import com.librerymanagement.util.IDGenerator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentalServiceImpl implements RentalService {
	private final RentalRepository rentalRepository;
	private final BookService bookService;
	private final UserService userService;

	public RentalServiceImpl() {
		this(new FileRentalRepository(), new BookServiceImpl(), new UserServiceImpl());
	}

	public RentalServiceImpl(RentalRepository rentalRepository, BookService bookService, UserService userService) {
		this.rentalRepository = rentalRepository;
		this.bookService = bookService;
		this.userService = userService;
	}

	@Override
	public String issueBook(String bookId, String userId) throws LibraryException {
		Book book = bookService.getBookById(bookId);
		userService.getUserById(userId);

		if (!book.isAvailable()) {
			throw new BookUnavailableException("Book is not available");
		}

		String rentalId = IDGenerator.generateRentalId();
		String issueDate = LocalDate.now().toString();
		Rental rental = new Rental(rentalId, bookId, userId, issueDate);

		bookService.updateBookAvailability(bookId, false);
		rentalRepository.append(rental);
		return rentalId;
	}

	@Override
	public void returnBook(String rentalId) throws LibraryException {
		List<Rental> rentals = rentalRepository.findAll();
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
		bookService.updateBookAvailability(rentalToReturn.getBookId(), true);
		rentalRepository.saveAll(rentals);
	}

	@Override
	public List<Rental> getRentalHistory(String userId) {
		List<Rental> history = new ArrayList<>();

		for (Rental rental : rentalRepository.findAll()) {
			if (rental.getUserId().equals(userId)) {
				history.add(rental);
			}
		}

		return history;
	}

	@Override
	public List<Rental> getAllRentals() {
		return rentalRepository.findAll();
	}
}
