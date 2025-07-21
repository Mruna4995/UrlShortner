package com.linkzy;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class RegisterHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            // Read request body
            InputStream is = exchange.getRequestBody();
            String body = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            System.out.println("Received registration data: " + body);

            // TODO: Parse JSON manually or using a library (like Gson), e.g. extract name, email, password
            // You can manually extract if not using Gson:
            // Example (very basic and not safe for prod):
            String name = body.split("\"name\":\"")[1].split("\"")[0];
            String email = body.split("\"email\":\"")[1].split("\"")[0];
            String password = body.split("\"password\":\"")[1].split("\"")[0];

            // TODO: Save to database if needed
            System.out.println("Registered User: " + name + ", " + email);

            // Send JSON response
            String response = "{\"message\":\"User registered successfully!\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }
}
