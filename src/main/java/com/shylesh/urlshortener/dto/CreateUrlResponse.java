package com.shylesh.urlshortener.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response payload for creating a new shortened URL")
public class CreateUrlResponse {

    @Schema(description = "The unique identifier of the URL mapping", example = "1")
    private Long id;

    @Schema(description = "The original URL that was shortened", example = "https://www.example.com")
    private String OriginalUrl;

    @Schema(description = "The generated short code for the original URL", example = "abc123")
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
