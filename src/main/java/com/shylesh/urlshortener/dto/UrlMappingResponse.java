package com.shylesh.urlshortener.dto;

import com.shylesh.urlshortener.entity.UrlMapping;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UrlMappingResponse {
    private Long id;
    private String originalUrl;
    private String shortCode;
    private Long clickCount;

    public UrlMappingResponse(Long id, String originalUrl, String shortCode, Long clickCount) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.clickCount = clickCount;
    }

    public UrlMappingResponse(UrlMapping urlMapping) {
        this.id = urlMapping.getId();
        this.originalUrl = urlMapping.getOriginalUrl();
        this.shortCode = urlMapping.getShortCode();
        this.clickCount = urlMapping.getClickCount();
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
