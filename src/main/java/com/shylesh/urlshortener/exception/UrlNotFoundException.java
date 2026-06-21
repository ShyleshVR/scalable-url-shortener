package com.shylesh.urlshortener.exception;

public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(String message) {
        super(message);
    }
    
    public UrlNotFoundException(Long id) {
        super("URL not found with id: " + id);
    }
}
