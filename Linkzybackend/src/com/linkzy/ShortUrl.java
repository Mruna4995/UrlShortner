package com.linkzy;

import java.util.*;

public class ShortUrl {

    private final Map<String, String> shortToOriginal = new HashMap<>();
    private final Map<String, String> originalToShort = new HashMap<>();
    private final String domain = "https://linkzy.in/";
    private final Random random = new Random();

    // Shorten a given URL
    public String shorten(String originalUrl) {
        if (originalToShort.containsKey(originalUrl)) {
            return originalToShort.get(originalUrl);
        }

        String shortCode;
        do {
            shortCode = generateShortCode(6);
        } while (shortToOriginal.containsKey(shortCode));

        String shortUrl = domain + shortCode;
        shortToOriginal.put(shortCode, originalUrl);
        originalToShort.put(originalUrl, shortUrl);

        return shortUrl;
    }

    // Get original URL from shortened code
    public String getOriginalUrl(String shortCode) {
        return shortToOriginal.get(shortCode);
    }

    // Get list of all shortened URLs
    public List<String> getAllShortenedUrls() {
        return new ArrayList<>(originalToShort.values());
    }

    // Generate a random alphanumeric code of given length
    private String generateShortCode(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
