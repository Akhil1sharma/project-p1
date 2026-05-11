package com.librerymanagement.model;

public class Book {
	private String bookId;
	private String title;
	private String author;
	private String category;
	private boolean isAvailable;

	public Book(String bookId, String title, String author, String category) {
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.category = category;
		this.isAvailable = true;
	}

	public String getBookId() {
		return bookId;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getCategory() {
		return category;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setTitle(String title) {
		if (title != null && !title.trim().isEmpty()) {
			this.title = title;
		}
	}

	public void setAuthor(String author) {
		if (author != null && !author.trim().isEmpty()) {
			this.author = author;
		}
	}

	public void setCategory(String category) {
		if (category != null && !category.trim().isEmpty()) {
			this.category = category;
		}
	}

	public void setAvailable(boolean available) {
		this.isAvailable = available;
	}
}
