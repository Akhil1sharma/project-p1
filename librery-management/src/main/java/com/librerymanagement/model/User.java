package com.librerymanagement.model;

public class User {
	private String userId;
	private String name;
	private String idCardNumber;

	public User(String userId, String name, String idCardNumber) {
		this.userId = userId;
		this.name = name;
		this.idCardNumber = idCardNumber;
	}

	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setName(String name) {
		if (name != null && !name.trim().isEmpty()) {
			this.name = name;
		}
	}

	public String toCSV() {
		return userId + "," + name + "," + idCardNumber;
	}

	public static User fromCSV(String csv) {
		String[] parts = csv.split(",");
		return new User(parts[0], parts[1], parts[2]);
	}
}
