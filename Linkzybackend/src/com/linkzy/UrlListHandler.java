package com.linkzy;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.List;

public class UrlListHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            // Get all shortened URLs from the database
            List<String> allUrls = Database.getAllShortenedUrls();

            // Convert the list of URLs into a JSON array
            StringBuilder json = new StringBuilder();
            json.append("[");
            for (int i = 0; i < allUrls.size(); i++) {
                json.append("\"").append(allUrls.get(i)).append("\"");
                if (i < allUrls.size() - 1) {
                    json.append(",");
                }
            }
            json.append("]");

            // Send the response as JSON
            byte[] responseBytes = json.toString().getBytes("UTF-8");
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, responseBytes.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }
        } else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, -1);
        }
    }
}
