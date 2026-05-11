package com.librerymanagement.repository;

import com.librerymanagement.model.Book;
import java.util.List;

public interface BookRepository {
	List<Book> findAll();
	void saveAll(List<Book> books);
	void append(Book book);
}
