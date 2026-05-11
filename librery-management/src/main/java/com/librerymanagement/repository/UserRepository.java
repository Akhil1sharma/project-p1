package com.librerymanagement.repository;

import com.librerymanagement.model.User;
import java.util.List;

public interface UserRepository {
	List<User> findAll();
	void saveAll(List<User> users);
	void append(User user);
}
