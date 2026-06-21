package com.shylesh.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateUrlRequest {

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
