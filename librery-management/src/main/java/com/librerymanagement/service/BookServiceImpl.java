package com.librerymanagement.service;

import com.librerymanagement.exception.BookNotFoundException;
import com.librerymanagement.exception.BookUnavailableException;
import com.librerymanagement.exception.InvalidOperationException;
import com.librerymanagement.exception.LibraryException;
import com.librerymanagement.model.Book;
import com.librerymanagement.repository.BookRepository;
import com.librerymanagement.repository.FileBookRepository;
import com.librerymanagement.util.IDGenerator;
import com.librerymanagement.util.Validator;
import java.util.List;

public class BookServiceImpl implements BookService {
	private final BookRepository repository;

	public BookServiceImpl() {
		this(new FileBookRepository());
	}

	public BookServiceImpl(BookRepository repository) {
		this.repository = repository;
	}

	@Override
	public String addBook(String title, String author, String category) throws LibraryException {
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
		repository.append(book);
		return bookId;
	}

	@Override
	public void updateBook(String bookId, String newTitle, String newAuthor, String newCategory)
			throws LibraryException {
		List<Book> books = repository.findAll();
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

		repository.saveAll(books);
	}

	@Override
	public void deleteBook(String bookId) throws LibraryException {
		List<Book> books = repository.findAll();
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
		repository.saveAll(books);
	}

	@Override
	public Book getBookById(String bookId) throws LibraryException {
		for (Book book : repository.findAll()) {
			if (book.getBookId().equals(bookId)) {
				return book;
			}
		}
		throw new BookNotFoundException("Book not found: " + bookId);
	}

	@Override
	public List<Book> getAllBooks() {
		return repository.findAll();
	}

	@Override
	public List<Book> searchByTitle(String query) {
		List<Book> results = new java.util.ArrayList<>();
		String normalized = query == null ? "" : query.toLowerCase();

		for (Book book : repository.findAll()) {
			if (book.getTitle().toLowerCase().contains(normalized)) {
				results.add(book);
			}
		}

		return results;
	}

	@Override
	public List<Book> searchByCategory(String category) {
		List<Book> results = new java.util.ArrayList<>();
		String normalized = category == null ? "" : category.toLowerCase();

		for (Book book : repository.findAll()) {
			if (book.getCategory().toLowerCase().contains(normalized)) {
				results.add(book);
			}
		}

		return results;
	}

	@Override
	public boolean isAvailable(String bookId) throws LibraryException {
		return getBookById(bookId).isAvailable();
	}

	@Override
	public void updateBookAvailability(String bookId, boolean available) throws LibraryException {
		List<Book> books = repository.findAll();
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

		repository.saveAll(books);
	}
}
