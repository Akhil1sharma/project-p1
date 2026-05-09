package com.librerymanagement.manager;

import com.librerymanagement.exception.BookNotFoundException;
import com.librerymanagement.exception.BookUnavailableException;
import com.librerymanagement.exception.InvalidOperationException;
import com.librerymanagement.exception.LibraryException;
import com.librerymanagement.model.Book;
import com.librerymanagement.util.FileUtil;
import com.librerymanagement.util.IDGenerator;
import com.librerymanagement.util.Validator;
import java.util.ArrayList;
import java.util.List;

public class BookManager {
	private static final String FILE_PATH = "data/books.txt";

	public void addBook(String title, String author, String category) throws LibraryException {
		if (!Validator.isNotEmpty(title)) {
			throw new InvalidOperationException("Title cannot be empty");
		}
		if (!Validator.isNotEmpty(author)) {
			throw new InvalidOperationException("Author cannot be empty");
		}
		if (!Validator.isNotEmpty(category)) {
			throw new InvalidOperationException("Category cannot be empty");
		}

		String bookId = IDGenerator.generateBookId();
		Book book = new Book(bookId, title, author, category);
		FileUtil.appendToFile(FILE_PATH, book.toCSV());
		System.out.println("Book added successfully. ID: " + bookId);
	}

	public void updateBook(String bookId, String newTitle, String newAuthor, String newCategory) throws LibraryException {
		List<Book> books = getAllBooks();
		boolean found = false;

		for (Book book : books) {
			if (book.getBookId().equals(bookId)) {
				book.setTitle(newTitle);
				book.setAuthor(newAuthor);
				book.setCategory(newCategory);
				found = true;
				break;
			}
		}

		if (!found) {
			throw new BookNotFoundException("Book not found: " + bookId);
		}

		writeAllBooks(books);
		System.out.println("Book updated successfully");
	}

	public void deleteBook(String bookId) throws LibraryException {
		List<Book> books = getAllBooks();
		Book bookToDelete = null;

		for (Book book : books) {
			if (book.getBookId().equals(bookId)) {
				bookToDelete = book;
				break;
			}
		}

		if (bookToDelete == null) {
			throw new BookNotFoundException("Book not found: " + bookId);
		}

		if (!bookToDelete.isAvailable()) {
			throw new BookUnavailableException("Cannot delete issued book");
		}

		books.remove(bookToDelete);
		writeAllBooks(books);
		System.out.println("Book deleted successfully");
	}

	public Book getBookById(String bookId) throws LibraryException {
		for (Book book : getAllBooks()) {
			if (book.getBookId().equals(bookId)) {
				return book;
			}
		}
		throw new BookNotFoundException("Book not found: " + bookId);
	}

	public List<Book> getAllBooks() {
		List<Book> books = new ArrayList<>();
		List<String> lines = FileUtil.readFile(FILE_PATH);

		for (String line : lines) {
			if (!line.trim().isEmpty()) {
				books.add(Book.fromCSV(line));
			}
		}

		return books;
	}

	public List<Book> searchByTitle(String query) {
		List<Book> results = new ArrayList<>();
		String normalized = query == null ? "" : query.toLowerCase();

		for (Book book : getAllBooks()) {
			if (book.getTitle().toLowerCase().contains(normalized)) {
				results.add(book);
			}
		}

		return results;
	}

	public List<Book> searchByCategory(String category) {
		List<Book> results = new ArrayList<>();
		String normalized = category == null ? "" : category.toLowerCase();

		for (Book book : getAllBooks()) {
			if (book.getCategory().toLowerCase().contains(normalized)) {
				results.add(book);
			}
		}

		return results;
	}

	public boolean isAvailable(String bookId) throws LibraryException {
		return getBookById(bookId).isAvailable();
	}

	public void updateBookAvailability(String bookId, boolean available) throws LibraryException {
		List<Book> books = getAllBooks();
		boolean found = false;

		for (Book book : books) {
			if (book.getBookId().equals(bookId)) {
				book.setAvailable(available);
				found = true;
				break;
			}
		}

		if (!found) {
			throw new BookNotFoundException("Book not found: " + bookId);
		}

		writeAllBooks(books);
	}

	private void writeAllBooks(List<Book> books) {
		List<String> csvLines = new ArrayList<>();
		for (Book book : books) {
			csvLines.add(book.toCSV());
		}
		FileUtil.writeFile(FILE_PATH, csvLines);
	}
}
