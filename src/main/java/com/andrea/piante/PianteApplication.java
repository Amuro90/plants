package com.andrea.piante;

import java.io.BufferedWriter;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PianteApplication {

	public static void main(String[] args) {
		SpringApplication.run(PianteApplication.class, args);
	}

	@PostConstruct
	public void createFile() {
		String directory = "/home/armbian/test";
		String fileName = "hello_world.txt";
		String content = "Hello world";

		File dir = new File(directory);
		if (!dir.exists()) {
			dir.mkdirs(); // Create directory if it doesn't exist
		}

		File file = new File(dir, fileName);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(content);
			System.out.println("File created successfully: " + file.getAbsolutePath());
		} catch (IOException e) {
			System.err.println("An error occurred while creating the file: " + e.getMessage());
		}
	}
}
