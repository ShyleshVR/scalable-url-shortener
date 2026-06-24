package com.shylesh.urlshortener.dto;

import com.shylesh.urlshortener.entity.UrlMapping;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

@Schema(description = "Response payload for retrieving a shortened URL mapping")
@NoArgsConstructor
public class UrlMappingResponse {
    @Schema(description = "The unique identifier of the URL mapping", example = "1")
    private Long id;

    @Schema(description = "The original URL before shortening", example = "https://www.example.com")
    private String originalUrl;

    @Schema(description = "The shortened URL code", example = "abc123")
    private String shortCode;

    @Schema(description = "The number of times the shortened URL has been clicked", example = "10")
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
