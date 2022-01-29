package ru.job4j.dream.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class ReadProperties {
    private static final Logger LOG = LoggerFactory.getLogger(ReadProperties.class);
    private final Properties properties = new Properties();

    public ReadProperties() {
        try {
            properties.load(ReadProperties.class.getClassLoader().getResourceAsStream("app.properties"));
        } catch (IOException e) {
            LOG.error("I/O exception in class constructor ReadProperties", e);
        }
    }

    public String getPath(String key) {
          return properties.getProperty(key);
    }
}
