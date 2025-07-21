package com.linkzy;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ShortenUrlHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            // Read the long URL from request body
            InputStream is = exchange.getRequestBody();
            String longUrl = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            // Generate short URL
            String shortCode = UUID.randomUUID().toString().substring(0, 6);
            String shortUrl = "linkzy.in/" + shortCode;

            // Save to database
            Database.saveUrl(shortUrl, longUrl);

            // Prepare JSON response
            String jsonResponse = "{\"shortUrl\": \"" + shortUrl + "\"}";

            // Send response
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            byte[] responseBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, responseBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();

        } else {
            // Method Not Allowed
            exchange.sendResponseHeaders(405, -1);
        }
    }
}
