package com.librerymanagement.service;

import com.librerymanagement.exception.InvalidOperationException;
import com.librerymanagement.exception.LibraryException;
import com.librerymanagement.exception.UserNotFoundException;
import com.librerymanagement.model.User;
import com.librerymanagement.repository.FileUserRepository;
import com.librerymanagement.repository.UserRepository;
import com.librerymanagement.util.IDGenerator;
import com.librerymanagement.util.Validator;
import java.util.List;

public class UserServiceImpl implements UserService {
	private final UserRepository repository;

	public UserServiceImpl() {
		this(new FileUserRepository());
	}

	public UserServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public String registerUser(String name, String idCardNumber) throws LibraryException {
		if (!Validator.isNotEmpty(name)) {
			throw new InvalidOperationException("Name cannot be empty");
		}
		if (!Validator.isNotEmpty(idCardNumber)) {
			throw new InvalidOperationException("ID Card number cannot be empty");
		}

		String userId = IDGenerator.generateUserId();
		User user = new User(userId, name, idCardNumber);
		repository.append(user);
		return userId;
	}

	@Override
	public User getUserById(String userId) throws LibraryException {
		for (User user : repository.findAll()) {
			if (user.getUserId().equals(userId)) {
				return user;
			}
		}
		throw new UserNotFoundException("User not found: " + userId);
	}

	@Override
	public List<User> getAllUsers() {
		return repository.findAll();
	}
}
