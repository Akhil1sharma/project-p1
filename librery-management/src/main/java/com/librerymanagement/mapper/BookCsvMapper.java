package com.librerymanagement.mapper;

import com.librerymanagement.model.Book;

public final class BookCsvMapper {
	private BookCsvMapper() {
	}

	public static String toCsv(Book book) {
		return book.getBookId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getCategory()
			+ "," + book.isAvailable();
	}

	public static Book fromCsv(String csv) {
		String[] parts = csv.split(",");
		Book book = new Book(parts[0], parts[1], parts[2], parts[3]);
		book.setAvailable(Boolean.parseBoolean(parts[4]));
		return book;
	}
}
