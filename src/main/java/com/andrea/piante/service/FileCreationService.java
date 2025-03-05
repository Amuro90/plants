package com.andrea.piante.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class FileCreationService {

    private static final Logger logger = LoggerFactory.getLogger(FileCreationService.class);

    @PostConstruct
    public void createFile() {
        String directory = "/home/armbian/test";
        String fileName = "hello_world.txt";
        String content = "Hello world";

        logger.info("Starting file creation process...");
        File dir = new File(directory);
        if (!dir.exists()) {
            boolean dirCreated = dir.mkdirs(); // Create directory if it doesn't exist
            if (dirCreated) {
                logger.info("Directory created: " + dir.getAbsolutePath());
            } else {
                logger.error("Failed to create directory: " + dir.getAbsolutePath());
                return;
            }
        }

        File file = new File(dir, fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
            logger.info("File created successfully: " + file.getAbsolutePath());
        } catch (IOException e) {
            logger.error("An error occurred while creating the file: " + e.getMessage(), e);
        }
    }
}
