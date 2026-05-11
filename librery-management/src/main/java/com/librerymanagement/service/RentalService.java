package com.librerymanagement.service;

import com.librerymanagement.exception.LibraryException;
import com.librerymanagement.model.Rental;
import java.util.List;

public interface RentalService {
	String issueBook(String bookId, String userId) throws LibraryException;
	void returnBook(String rentalId) throws LibraryException;
	List<Rental> getRentalHistory(String userId);
	List<Rental> getAllRentals();
}
