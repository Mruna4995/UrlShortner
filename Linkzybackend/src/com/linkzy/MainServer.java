package com.linkzy;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.IOException;

public class MainServer {
    public static void main(String[] args) throws IOException {
    	HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
    	server.createContext("/shorten", new ShortenUrlHandler());
    	server.createContext("/urls", new UrlListHandler());
    	server.createContext("/register", new RegisterHandler());
    	server.setExecutor(null);
    	server.start();

    	System.out.println("Server started at http://localhost:8000");
    }
}
