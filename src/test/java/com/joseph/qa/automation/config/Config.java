package com.joseph.qa.automation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Properties props = new Properties();

    static {
        try (InputStream is = Config.class.getClassLoader()
                .getResourceAsStream("config/application.properties")) {

            if (is == null) {
                throw new RuntimeException("Arquivo config/application.properties não encontrado em src/test/resources");
            }

            props.load(is);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar application.properties", e);
        }
    }

    public static String get(String key) {
        String value = System.getProperty(key); // permite override via -Dbase.url=...
        if (value != null && !value.isBlank()) return value;

        value = props.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new RuntimeException("Config key não encontrada ou vazia: " + key);
        }
        return value.trim();
    }
}