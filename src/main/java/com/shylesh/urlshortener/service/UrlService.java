package com.shylesh.urlshortener.service;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shylesh.urlshortener.dto.CreateUrlRequest;
import com.shylesh.urlshortener.dto.CreateUrlResponse;
import com.shylesh.urlshortener.entity.UrlMapping;
import com.shylesh.urlshortener.repository.UrlMappingRepository;
import com.shylesh.urlshortener.exception.UrlNotFoundException;

@Service
public class UrlService {

    private static final String BASE62_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    public CreateUrlResponse saveUrl(CreateUrlRequest request) {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(request.getUrl());
        urlMapping.setCreatedAt(new Date());

        UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);
        savedUrlMapping.setShortUrl(encodeBase62(savedUrlMapping.getId()));
        savedUrlMapping = urlMappingRepository.save(savedUrlMapping);

        CreateUrlResponse response = new CreateUrlResponse(savedUrlMapping.getId(), savedUrlMapping.getOriginalUrl(), savedUrlMapping.getShortUrl());
        return response;
    }

    public UrlMapping getById(Long id) {
        return urlMappingRepository.findById(id).orElseThrow(() -> new UrlNotFoundException(id));
    }

    public List<UrlMapping> getAllUrls() {
        return urlMappingRepository.findAll();
    }

    public UrlMapping getByShortUrl(String shortUrl) {
        return urlMappingRepository.findByShortUrl(shortUrl).orElseThrow(() -> new UrlNotFoundException("URL not found for short code: " + shortUrl));
    }

    private String encodeBase62(long id) {
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

}
