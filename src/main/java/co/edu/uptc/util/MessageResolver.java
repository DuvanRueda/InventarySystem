package co.edu.uptc.util;

import java.util.List;
import java.util.stream.Collectors;

import co.edu.uptc.config.i18n.I18nConfig;

public class MessageResolver {

    private final I18nConfig i18n;

    public MessageResolver(I18nConfig i18n) {
        this.i18n = i18n;
    }
    
    public List<String> resolve(List<String> rawErrors) {
        return rawErrors.stream()
                .map(this::resolveOne)
                .collect(Collectors.toList());
    }

    private String resolveOne(String raw) {
        if (raw == null || raw.isBlank()) return "";
        String[] parts = raw.split("\\|", 2);
        String key = parts[0];
        String param = parts.length > 1 ? parts[1] : "";
        String message = i18n.get(key);
        return param.isBlank() ? message : message.replace("{0}", param);
    }
}
