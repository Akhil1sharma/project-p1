package com.librerymanagement;

import java.util.List;
import java.util.Scanner;

import com.librerymanagement.exception.LibraryException;
import com.librerymanagement.model.Book;
import com.librerymanagement.repository.RepositoryBootstrap;
import com.librerymanagement.service.BookService;
import com.librerymanagement.service.BookServiceImpl;
import com.librerymanagement.service.RentalService;
import com.librerymanagement.service.RentalServiceImpl;
import com.librerymanagement.service.UserService;
import com.librerymanagement.service.UserServiceImpl;

public class Main {
	private static final BookService bookService = new BookServiceImpl();
	private static final UserService userService = new UserServiceImpl();
	private static final RentalService rentalService = new RentalServiceImpl();
	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		initializeFiles();

		while (true) {
			displayMenu();
			int choice = getMenuChoice();

			try {
				switch (choice) {
					case 1:
						addBook();
						break;
					case 2:
						updateBook();
						break;
					case 3:
						deleteBook();
						break;
					case 4:
						checkAvailability();
						break;
					case 5:
						searchByTitle();
						break;
					case 6:
						searchByCategory();
						break;
					case 7:
						issueBook();
						break;
					case 8:
						returnBook();
						break;
					case 9:
						listAllBooks();
						break;
					case 10:
						registerUser();
						break;
					case 0:
						System.out.println("Thank you for using Library Management System.");
						return;
					default:
						System.out.println("Invalid choice. Please try again.");
				}
			} catch (LibraryException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	private static void displayMenu() {
		System.out.println();
		System.out.println("========================================");
		System.out.println("        LIBRARY MANAGEMENT SYSTEM       ");
		System.out.println("========================================");
		System.out.println("1. Add Book");
		System.out.println("2. Update Book");
		System.out.println("3. Delete Book");
		System.out.println("4. Check Availability");
		System.out.println("5. Search by Title");
		System.out.println("6. Search by Category");
		System.out.println("7. Issue Book (Rent)");
		System.out.println("8. Return Book");
		System.out.println("9. List All Books");
		System.out.println("10. Register User");
		System.out.println("0. Exit");
		System.out.println("========================================");
		System.out.print("Enter choice: ");
	}

	private static void addBook() throws LibraryException {
		System.out.print("Enter title: ");
		String title = scanner.nextLine();
		System.out.print("Enter author: ");
		String author = scanner.nextLine();
		System.out.print("Enter category: ");
		String category = scanner.nextLine();

		String bookId = bookService.addBook(title, author, category);
		System.out.println("Book added successfully. ID: " + bookId);
	}

	private static void updateBook() throws LibraryException {
		System.out.print("Enter book ID: ");
		String bookId = scanner.nextLine();
		System.out.print("Enter new title (press Enter to skip): ");
		String title = scanner.nextLine();
		System.out.print("Enter new author (press Enter to skip): ");
		String author = scanner.nextLine();
		System.out.print("Enter new category (press Enter to skip): ");
		String category = scanner.nextLine();

		bookService.updateBook(bookId,
			title.isEmpty() ? null : title,
			author.isEmpty() ? null : author,
			category.isEmpty() ? null : category);
		System.out.println("Book updated successfully");
	}

	private static void deleteBook() throws LibraryException {
		System.out.print("Enter book ID: ");
		String bookId = scanner.nextLine();
		bookService.deleteBook(bookId);
		System.out.println("Book deleted successfully");
	}

	private static void checkAvailability() throws LibraryException {
		System.out.print("Enter book ID: ");
		String bookId = scanner.nextLine();
		boolean available = bookService.isAvailable(bookId);
		System.out.println("Status: " + (available ? "Available" : "Not Available"));
	}

	private static void searchByTitle() {
		System.out.print("Enter search term: ");
		String query = scanner.nextLine();
		List<Book> results = bookService.searchByTitle(query);
		displayBooks(results);
	}

	private static void searchByCategory() {
		System.out.print("Enter category: ");
		String category = scanner.nextLine();
		List<Book> results = bookService.searchByCategory(category);
		displayBooks(results);
	}

	private static void issueBook() throws LibraryException {
		System.out.print("Enter book ID: ");
		String bookId = scanner.nextLine();
		System.out.print("Enter user ID: ");
		String userId = scanner.nextLine();

		String rentalId = rentalService.issueBook(bookId, userId);
		System.out.println("Book issued successfully. Rental ID: " + rentalId);
	}

	private static void returnBook() throws LibraryException {
		System.out.print("Enter rental ID: ");
		String rentalId = scanner.nextLine();
		rentalService.returnBook(rentalId);
		System.out.println("Book returned successfully");
	}

	private static void listAllBooks() {
		List<Book> books = bookService.getAllBooks();
		displayBooks(books);
	}

	private static void registerUser() throws LibraryException {
		System.out.print("Enter name: ");
		String name = scanner.nextLine();
		System.out.print("Enter ID card number: ");
		String idCard = scanner.nextLine();
		String userId = userService.registerUser(name, idCard);
		System.out.println("User registered successfully. ID: " + userId);
	}

	private static void displayBooks(List<Book> books) {
		if (books.isEmpty()) {
			System.out.println("No books found.");
			return;
		}

		System.out.println();
		System.out.println("--------------------------------------------------------------------------------");
		System.out.printf("%-8s | %-20s | %-15s | %-15s | %-12s%n",
			"ID", "Title", "Author", "Category", "Available");
		System.out.println("--------------------------------------------------------------------------------");
		for (Book book : books) {
			System.out.printf("%-8s | %-20s | %-15s | %-15s | %-12s%n",
				book.getBookId(),
				truncate(book.getTitle(), 20),
				truncate(book.getAuthor(), 15),
				truncate(book.getCategory(), 15),
				book.isAvailable() ? "Yes" : "No");
		}
		System.out.println("--------------------------------------------------------------------------------");
	}

	private static String truncate(String value, int maxLength) {
		if (value == null) {
			return "";
		}
		if (maxLength <= 0 || value.length() <= maxLength) {
			return value;
		}
		return value.substring(0, maxLength - 1);
	}

	private static int getMenuChoice() {
		String raw = scanner.nextLine();
		try {
			return Integer.parseInt(raw.trim());
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	private static void initializeFiles() {
		RepositoryBootstrap.ensureDataFiles();
	}
}
