package com.shylesh.urlshortener.dto;

public class CreateUrlRequest {
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
