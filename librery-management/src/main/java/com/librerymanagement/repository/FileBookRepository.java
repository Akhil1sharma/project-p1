package com.librerymanagement.repository;

import com.librerymanagement.mapper.BookCsvMapper;
import com.librerymanagement.model.Book;
import com.librerymanagement.util.FileUtil;
import java.util.ArrayList;
import java.util.List;

public class FileBookRepository implements BookRepository {
	private static final String FILE_PATH = "data/books.txt";

	public FileBookRepository() {
		RepositoryBootstrap.ensureDataFiles();
	}

	@Override
	public List<Book> findAll() {
		List<Book> books = new ArrayList<>();
		List<String> lines = FileUtil.readFile(FILE_PATH);

		for (String line : lines) {
			if (!line.trim().isEmpty()) {
				books.add(BookCsvMapper.fromCsv(line));
			}
		}

		return books;
	}

	@Override
	public void saveAll(List<Book> books) {
		List<String> csvLines = new ArrayList<>();
		for (Book book : books) {
			csvLines.add(BookCsvMapper.toCsv(book));
		}
		FileUtil.writeFile(FILE_PATH, csvLines);
	}

	@Override
	public void append(Book book) {
		FileUtil.appendToFile(FILE_PATH, BookCsvMapper.toCsv(book));
	}
}
