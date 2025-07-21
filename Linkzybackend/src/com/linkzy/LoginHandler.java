package com.linkzy;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class LoginHandler implements HttpHandler {

    // Dummy user credentials for example
    private static final String DUMMY_EMAIL = "test@example.com";
    private static final String DUMMY_PASSWORD = "123456";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            // Read JSON input from request body
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            // Example input JSON: {"email":"test@example.com","password":"123456"}
            String body = requestBody.toString();
            String email = extractField(body, "email");
            String password = extractField(body, "password");

            String response;
            int status;

            if (DUMMY_EMAIL.equals(email) && DUMMY_PASSWORD.equals(password)) {
                response = "{\"status\":\"success\",\"message\":\"Login successful\"}";
                status = 200;
            } else {
                response = "{\"status\":\"error\",\"message\":\"Invalid credentials\"}";
                status = 401;
            }

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(status, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private String extractField(String json, String fieldName) {
        // crude field extraction (use Jackson or Gson for production)
        String key = "\"" + fieldName + "\":\"";
        int start = json.indexOf(key);
        if (start == -1) return "";
        start += key.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }
}
