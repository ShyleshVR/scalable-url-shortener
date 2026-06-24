package com.shylesh.urlshortener.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@Schema(description = "Request payload for creating a new shortened URL")
public class CreateUrlRequest {

    @Schema(description = "The original URL to be shortened", example = "https://www.example.com")
    @NotBlank
    @Pattern(regexp = "^(http|https)://.*$", message = "URL must start with http:// or https://", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String url;

    public CreateUrlRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
