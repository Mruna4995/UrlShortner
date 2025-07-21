package com.linkzy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String URL = "jdbc:h2:./data/urlshortenerdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    static {
        initializeDatabase(); // Automatically create table at startup
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS urls (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "short_url VARCHAR(255) UNIQUE, " +
                    "long_url VARCHAR(2048))");

            System.out.println("✅ Database initialized.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveUrl(String shortUrl, String longUrl) {
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO urls (short_url, long_url) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, shortUrl);
            stmt.setString(2, longUrl);
            stmt.executeUpdate();
            System.out.println("🔗 Saved: " + shortUrl + " → " + longUrl);
        } catch (SQLException e) {
            System.err.println("❌ Failed to save URL: " + e.getMessage());
        }
    }

    // Fetch all shortened URLs
    public static List<String> getAllShortenedUrls() {
        List<String> allUrls = new ArrayList<>();
        String query = "SELECT short_url FROM urls";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                allUrls.add(rs.getString("short_url"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUrls;
    }
}
