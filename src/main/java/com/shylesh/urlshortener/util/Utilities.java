package com.shylesh.urlshortener.util;

import com.shylesh.urlshortener.dto.UrlMappingResponse;
import com.shylesh.urlshortener.entity.UrlMapping;

public class Utilities {

    private static final String BASE62_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static String encodeBase62(long id) {
        if (id == 0) {
            return String.valueOf(BASE62_CHARACTERS.charAt(0));
        }

        StringBuilder shortUrl = new StringBuilder();
        while (id > 0) {
            int remainder = (int) (id % 62);
            shortUrl.append(BASE62_CHARACTERS.charAt(remainder));
            id = id / 62;
        }

        return shortUrl.reverse().toString();
    }

    public static UrlMappingResponse mapToResponse(UrlMapping urlMapping) {

    UrlMappingResponse response = new UrlMappingResponse();

    response.setId(urlMapping.getId());
    response.setOriginalUrl(urlMapping.getOriginalUrl());
    response.setShortCode(urlMapping.getShortCode());
    response.setClickCount(urlMapping.getClickCount());

    return response;
}
}
