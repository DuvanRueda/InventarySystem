package co.edu.uptc.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadConfig {

    private static LoadConfig instance;
    private Properties props;

    private LoadConfig() {
        props = new Properties();
        loadDefaultConfig();
        loadExternalConfig();
    }

    private void loadDefaultConfig() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Error loading config.properties", e);
        }
    }

    private void loadExternalConfig() {
        Properties temp = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            temp.load(input);
            validatePersonConfig(temp);
            validatePositiveDouble(temp, "product.price.max");
            String lan = temp.getProperty("lan");
            props.setProperty("lan", lan);
        } catch (Exception e) {
            createExternalFile();
        }
    }

    private void createExternalFile() {
        try (FileOutputStream fos = new FileOutputStream("config.properties")) {
            props.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void validatePersonConfig(Properties temp) {
        mergeRange(temp, "person.name.length.min", "person.name.length.max");
        mergeRange(temp, "person.lastname.length.min", "person.lastname.length.max");
        validatePositiveInt(temp, "person.list.length");
    }

    private void mergeRange(Properties temp, String minKey, String maxKey) {
        int min = tryParseInt(temp.getProperty(minKey));
        int max = tryParseInt(temp.getProperty(maxKey));
        if (min == -1 || max == -1) return;
        if (min >= 0 && max >= 0 && min <= max) {
            props.setProperty(minKey, String.valueOf(min));
            props.setProperty(maxKey, String.valueOf(max));
        }
    }

    private void validatePositiveInt(Properties temp, String key) {
        String value = temp.getProperty(key);
        if (value == null || tryParseInt(value) < 1) return;
        props.setProperty(key, value);
    }

    private void validatePositiveDouble(Properties temp, String key) {
        String value = temp.getProperty(key);
        if (value == null || tryParseDouble(value) < 0) return;
            props.setProperty(key, value);
    }

    private int tryParseInt(String value) {
        try {
            if (value == null) return -1;
            return Integer.parseInt(value);
        } catch (Exception e) {
            return -1;
        }
    }

    private double tryParseDouble(String value) {
        try {
            if (value == null) return -1;
            return Double.parseDouble(value);
        } catch (Exception e) {
            return -1;
        }
    }

    public static LoadConfig getInstance() {
        if (instance == null) {
            instance = new LoadConfig();
        }
        return instance;
    }

    public String get(String key) {
        return props.getProperty(key);
    }
}
