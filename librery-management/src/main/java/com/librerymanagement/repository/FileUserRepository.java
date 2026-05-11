package com.librerymanagement.repository;

import com.librerymanagement.mapper.UserCsvMapper;
import com.librerymanagement.model.User;
import com.librerymanagement.util.FileUtil;
import java.util.ArrayList;
import java.util.List;

public class FileUserRepository implements UserRepository {
	private static final String FILE_PATH = "data/users.txt";

	public FileUserRepository() {
		RepositoryBootstrap.ensureDataFiles();
	}

	@Override
	public List<User> findAll() {
		List<User> users = new ArrayList<>();
		List<String> lines = FileUtil.readFile(FILE_PATH);

		for (String line : lines) {
			if (!line.trim().isEmpty()) {
				users.add(UserCsvMapper.fromCsv(line));
			}
		}

		return users;
	}

	@Override
	public void saveAll(List<User> users) {
		List<String> csvLines = new ArrayList<>();
		for (User user : users) {
			csvLines.add(UserCsvMapper.toCsv(user));
		}
		FileUtil.writeFile(FILE_PATH, csvLines);
	}

	@Override
	public void append(User user) {
		FileUtil.appendToFile(FILE_PATH, UserCsvMapper.toCsv(user));
	}
}
