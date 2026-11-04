package com.ruoyi;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

final class DatabaseProperties {
    private DatabaseProperties() { }

    static Properties load() throws IOException {
        Properties properties = new Properties();
        Path directory = Path.of("").toAbsolutePath();
        while (directory != null && !Files.isRegularFile(directory.resolve(".env"))) {
            directory = directory.getParent();
        }
        if (directory != null) {
            try (Reader reader = Files.newBufferedReader(directory.resolve(".env"), StandardCharsets.UTF_8)) {
                properties.load(reader);
            }
        }
        for (String key : new String[]{"DB_URL", "DB_USERNAME", "DB_PASSWORD"}) {
            String value = System.getenv(key);
            if (value != null && !value.isBlank()) properties.setProperty(key, value);
            if (properties.getProperty(key, "").isBlank()) {
                throw new IllegalStateException("Missing " + key + " in root .env or environment variables");
            }
        }
        return properties;
    }
}
