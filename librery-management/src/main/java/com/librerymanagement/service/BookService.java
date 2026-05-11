package com.librerymanagement.service;

import com.librerymanagement.exception.LibraryException;
import com.librerymanagement.model.Book;
import java.util.List;

public interface BookService {
	String addBook(String title, String author, String category) throws LibraryException;
	void updateBook(String bookId, String newTitle, String newAuthor, String newCategory) throws LibraryException;
	void deleteBook(String bookId) throws LibraryException;
	Book getBookById(String bookId) throws LibraryException;
	List<Book> getAllBooks();
	List<Book> searchByTitle(String query);
	List<Book> searchByCategory(String category);
	boolean isAvailable(String bookId) throws LibraryException;
	void updateBookAvailability(String bookId, boolean available) throws LibraryException;
}
