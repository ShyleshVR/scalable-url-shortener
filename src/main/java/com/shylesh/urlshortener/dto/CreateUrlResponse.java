package com.shylesh.urlshortener.dto;

public class CreateUrlResponse {
    private Long id;
    private String OriginalUrl;
    private String shortUrl;

    public CreateUrlResponse(Long id, String originalUrl, String shortUrl) {
        this.id = id;
        this.OriginalUrl = originalUrl;
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return OriginalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        OriginalUrl = originalUrl;
    }
}
