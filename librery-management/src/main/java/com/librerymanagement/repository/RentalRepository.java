package com.librerymanagement.repository;

import com.librerymanagement.model.Rental;
import java.util.List;

public interface RentalRepository {
	List<Rental> findAll();
	void saveAll(List<Rental> rentals);
	void append(Rental rental);
}
