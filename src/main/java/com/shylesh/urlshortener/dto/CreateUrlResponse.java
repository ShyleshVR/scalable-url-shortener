package com.shylesh.urlshortener.dto;

public class CreateUrlResponse {
    private Long id;
    private String OriginalUrl;
    private String shortCode;

    public CreateUrlResponse(Long id, String originalUrl, String shortCode) {
        this.id = id;
        this.OriginalUrl = originalUrl;
        this.shortCode = shortCode;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
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
