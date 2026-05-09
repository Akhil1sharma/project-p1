package com.librerymanagement.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	public static List<String> readFile(String filePath) {
		List<String> lines = new ArrayList<>();
		File file = new File(filePath);

		if (!file.exists()) {
			return lines;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}

		return lines;
	}

	public static void writeFile(String filePath, List<String> lines) {
		ensureParentExists(filePath);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for (String line : lines) {
				writer.write(line);
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Error writing file: " + e.getMessage());
		}
	}

	public static void appendToFile(String filePath, String line) {
		ensureParentExists(filePath);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
			writer.write(line);
			writer.newLine();
		} catch (IOException e) {
			System.err.println("Error appending to file: " + e.getMessage());
		}
	}

	public static boolean fileExists(String filePath) {
		return new File(filePath).exists();
	}

	public static void createFile(String filePath) {
		File file = new File(filePath);
		ensureParentExists(filePath);
		if (file.exists()) {
			return;
		}

		try {
			if (!file.createNewFile()) {
				System.err.println("File already exists: " + filePath);
			}
		} catch (IOException e) {
			System.err.println("Error creating file: " + e.getMessage());
		}
	}

	private static void ensureParentExists(String filePath) {
		File parent = new File(filePath).getParentFile();
		if (parent != null && !parent.exists()) {
			if (!parent.mkdirs()) {
				System.err.println("Error creating directory: " + parent.getPath());
			}
		}
	}
}
