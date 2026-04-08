package co.edu.uptc.config.i18n;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class I18nConfig {

    private static final String EXTERNAL_DIR = "lan";
    private static final String BASE_NAME = "i18n/messages";
    private static final String[] DEFAULT_LOCALES = {"es", "en", "pt"};

    private static I18nConfig instance;

    private ResourceBundle bundle;

    private I18nConfig(String lang) {
        exportDefaultsIfMissing();
        load(lang);
    }

    public static I18nConfig getInstance(String lang) {
        if (instance == null) {
            instance = new I18nConfig(lang);
        }
        return instance;
    }

    public static I18nConfig getInstance() {
        return instance;
    }

    public String get(String key) {
        return bundle.getString(key);
    }

    private void load(String lang) {
        Locale locale = Locale.of(lang);
        loadBundle(locale, lang);
    }


    private void loadBundle(Locale locale, String lang) {
        Path path = Paths.get(EXTERNAL_DIR, "messages_" + lang + ".properties");

        if (Files.exists(path)) {
            try (InputStream is = new FileInputStream(path.toFile())) {
                bundle = new PropertyResourceBundle(new InputStreamReader(is, StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
                bundle = ResourceBundle.getBundle(BASE_NAME);
            }
        }
    }

    private void exportDefaultsIfMissing() {
        File dir = new File(EXTERNAL_DIR);
        if (!dir.exists()) dir.mkdirs();

        for (String locale : DEFAULT_LOCALES) {
            File target = new File(dir, "messages_" + locale + ".properties");

            if (!target.exists()) {
                copyInternal("i18n/messages_" + locale + ".properties", target);
            }
        }
    }

    private void copyInternal(String resourceName, File target) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (is == null) return;
            Files.copy(is, target.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
