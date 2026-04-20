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
                throw new RuntimeException(propertyFileName + " not found in classpath");
            }

            PROPERTIES.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties", e);
        }
    }


    protected static String getRequired(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required property: " + key);
        }
        return value;
    }

    protected static String getOptional(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }
}