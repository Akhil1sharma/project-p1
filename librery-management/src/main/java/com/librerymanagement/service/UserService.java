package com.librerymanagement.service;

import com.librerymanagement.exception.LibraryException;
import com.librerymanagement.model.User;
import java.util.List;

public interface UserService {
	String registerUser(String name, String idCardNumber) throws LibraryException;
	User getUserById(String userId) throws LibraryException;
	List<User> getAllUsers();
}
