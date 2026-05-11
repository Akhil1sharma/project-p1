package com.librerymanagement.mapper;

import com.librerymanagement.model.User;

public final class UserCsvMapper {
	private UserCsvMapper() {
	}

	public static String toCsv(User user) {
		return user.getUserId() + "," + user.getName() + "," + user.getIdCardNumber();
	}

	public static User fromCsv(String csv) {
		String[] parts = csv.split(",");
		return new User(parts[0], parts[1], parts[2]);
	}
}
