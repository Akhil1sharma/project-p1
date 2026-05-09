package com.librerymanagement.manager;

import com.librerymanagement.exception.InvalidOperationException;
import com.librerymanagement.exception.LibraryException;
import com.librerymanagement.exception.UserNotFoundException;
import com.librerymanagement.model.User;
import com.librerymanagement.util.FileUtil;
import com.librerymanagement.util.IDGenerator;
import com.librerymanagement.util.Validator;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
	private static final String FILE_PATH = "data/users.txt";

	public void registerUser(String name, String idCardNumber) throws LibraryException {
		if (!Validator.isNotEmpty(name)) {
			throw new InvalidOperationException("Name cannot be empty");
		}
		if (!Validator.isNotEmpty(idCardNumber)) {
			throw new InvalidOperationException("ID Card number cannot be empty");
		}

		String userId = IDGenerator.generateUserId();
		User user = new User(userId, name, idCardNumber);
		FileUtil.appendToFile(FILE_PATH, user.toCSV());
		System.out.println("User registered successfully. ID: " + userId);
	}

	public User getUserById(String userId) throws LibraryException {
		for (User user : getAllUsers()) {
			if (user.getUserId().equals(userId)) {
				return user;
			}
		}
		throw new UserNotFoundException("User not found: " + userId);
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		List<String> lines = FileUtil.readFile(FILE_PATH);

		for (String line : lines) {
			if (!line.trim().isEmpty()) {
				users.add(User.fromCSV(line));
			}
		}

		return users;
	}
}
