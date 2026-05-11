package com.librerymanagement.repository;

import com.librerymanagement.mapper.RentalCsvMapper;
import com.librerymanagement.model.Rental;
import com.librerymanagement.util.FileUtil;
import java.util.ArrayList;
import java.util.List;

public class FileRentalRepository implements RentalRepository {
	private static final String FILE_PATH = "data/rentals.txt";

	public FileRentalRepository() {
		RepositoryBootstrap.ensureDataFiles();
	}

	@Override
	public List<Rental> findAll() {
		List<Rental> rentals = new ArrayList<>();
		List<String> lines = FileUtil.readFile(FILE_PATH);

		for (String line : lines) {
			if (!line.trim().isEmpty()) {
				rentals.add(RentalCsvMapper.fromCsv(line));
			}
		}

		return rentals;
	}

	@Override
	public void saveAll(List<Rental> rentals) {
		List<String> csvLines = new ArrayList<>();
		for (Rental rental : rentals) {
			csvLines.add(RentalCsvMapper.toCsv(rental));
		}
		FileUtil.writeFile(FILE_PATH, csvLines);
	}

	@Override
	public void append(Rental rental) {
		FileUtil.appendToFile(FILE_PATH, RentalCsvMapper.toCsv(rental));
	}
}
