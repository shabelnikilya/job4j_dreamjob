package ru.job4j.dream.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReadProperties {
    private static final Logger LOG = LoggerFactory.getLogger(ReadProperties.class);
    private final Map<String, String> value = new HashMap<>();

    public ReadProperties(String path) {
        validPath(path);
        try (BufferedReader read = new BufferedReader(
                new FileReader(path))) {
            read.lines()
                    .filter(line -> line.charAt(0) != '#')
                    .forEach(line -> {
                        validFileProperties(path, line);
                        value.put(line.split("=")[0], line.split("=")[1]);
                    });
        } catch (IOException e) {
            LOG.error("I/O exception in class constructor ReadProperties", e);
        }
    }

    public String getPath(String key) {
        return value.get(key);
    }

    public void validFileProperties(String path, String line) {
        File properties = new File(path);
        if (line == null || "".equals(line)) {
            throw new IllegalArgumentException("Empty line in the file - " + properties.getName());
        }
        if (!line.contains("=") || line.charAt(0) == '=' || line.split("=").length != 2) {
            throw new IllegalArgumentException("Incorrect key value pairs in the file - " + properties.getName());
        }
    }

    public void validPath(String path) {
        File file = new File(path);
        if (!file.exists() || file.isDirectory()) {
            throw new IllegalArgumentException("The file path is specified incorrectly");
        }
    }
}
