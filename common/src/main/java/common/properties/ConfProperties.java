package common.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfProperties {

    private static final Properties PROPERTIES = new Properties();

    protected void loadProperties(String propertyFileName) {
        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream(propertyFileName)) {

            if (input == null) {
//                throw new RuntimeException(propertyFileName + " not found in classpath");
                System.out.println("Properties file " + propertyFileName + " not found. Using environment variables.");
            }
            else
                PROPERTIES.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties", e);
        }
    }

    protected static String getFromEnv(String key) {
        String envKey = key
                .toUpperCase()
                .replace('.', '_');
        return System.getenv(envKey);
    }

    protected static String getRequired(String key) {
        String envValue = getFromEnv(key);

        if (envValue == null || envValue.isBlank()) {
            String propertyValue = PROPERTIES.getProperty(key);
            if (propertyValue == null || propertyValue.isBlank()) {
                throw new IllegalStateException("Missing required property: " + key);
            }
            return propertyValue;
        }
        return envValue;
    }

    protected static String getOptional(String key, String defaultValue) {
        String envValue = getFromEnv(key);

        if (envValue == null || envValue.isBlank()) {
            return PROPERTIES.getProperty(key, defaultValue);
        }
        return envValue;
    }
}