package com.url.shortener;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class UrlShortenerService {

    public static final String HOST = "http://localhost:8080/";

    private final Map<String, String> urlMap = new HashMap<>();

    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final int SHORT_URL_LENGTH = 7;

    public String shortenUrl(final String originalUrl) {

        // check if the url is valid
        if (!isValidUrl(originalUrl)) throw new IllegalArgumentException("Invalid URL format");

        // check if the url already exists
        for (Map.Entry<String, String> entry : urlMap.entrySet()) {
            if (entry.getValue().equals(originalUrl)) return HOST + entry.getKey();
        }

        String shortCode = generateShortCode();
        while (urlMap.containsKey(shortCode)) {
            shortCode = generateShortCode();
        }

        urlMap.put(shortCode, originalUrl);

        return HOST + shortCode;
    }


    private String generateShortCode() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            sb.append(BASE62.charAt(random.nextInt(BASE62.length())));
        }
        return sb.toString();
    }

    public String shortenUrlUsingUuid(final String originalUrl) {

        if (!isValidUrl(originalUrl)) {
            throw new IllegalArgumentException("Invalid URL format.");
        }

        // check if the url already exists
        for (Map.Entry<String, String> entry : urlMap.entrySet()) {
            if (entry.getValue().equals(originalUrl)) {
                return HOST + entry.getKey();
            }
        }

        String shortCode = UUID.randomUUID().toString().substring(0, 7);

        while (urlMap.containsKey(shortCode)) {
            shortCode = UUID.randomUUID().toString().substring(0, 7);
        }
        urlMap.put(shortCode, originalUrl);

        return HOST + shortCode;
    }

    public String getLongUrl(final String key) {
        return urlMap.get(key);
    }

    private boolean isValidUrl(final String url) {

        UrlValidator urlValidator = new UrlValidator();
        return urlValidator.isValid(url);
    }


}
