package com.bridgelabz.util;

import com.bridgelabz.exception.DatabaseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfig {

    private static final String CONFIG_FILE = "application.properties";
    private static final ApplicationConfig INSTANCE = new ApplicationConfig();

    private final Properties properties = new Properties();

    private ApplicationConfig() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new DatabaseException(CONFIG_FILE + " not found in classpath");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new DatabaseException("Unable to load database configuration", e);
        }
    }

    public static ApplicationConfig getInstance() {
        return INSTANCE;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public int getIntProperty(String key) {
        try {
            return Integer.parseInt(getProperty(key));
        } catch (NumberFormatException e) {
            throw new DatabaseException("Invalid integer configuration for " + key, e);
        }
    }
}
