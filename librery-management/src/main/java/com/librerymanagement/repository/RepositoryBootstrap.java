package com.librerymanagement.repository;

import com.librerymanagement.util.FileUtil;

public final class RepositoryBootstrap {
	private static boolean initialized = false;

	private RepositoryBootstrap() {
	}

	public static synchronized void ensureDataFiles() {
		if (initialized) {
			return;
		}

		String[] files = {"data/books.txt", "data/users.txt", "data/rentals.txt"};
		for (String file : files) {
			if (!FileUtil.fileExists(file)) {
				FileUtil.createFile(file);
			}
		}

		initialized = true;
	}
}
