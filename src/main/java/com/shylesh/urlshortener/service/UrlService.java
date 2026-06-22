package com.shylesh.urlshortener.service;

import java.util.List;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shylesh.urlshortener.dto.CreateUrlRequest;
import com.shylesh.urlshortener.dto.CreateUrlResponse;
import com.shylesh.urlshortener.dto.UrlMappingResponse;
import com.shylesh.urlshortener.entity.UrlMapping;
import com.shylesh.urlshortener.repository.UrlMappingRepository;
import com.shylesh.urlshortener.exception.UrlNotFoundException;

import com.shylesh.urlshortener.util.Utilities;

@Service
public class UrlService {

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @Autowired
    private RedisService redisService;

    private static final Logger logger = LoggerFactory.getLogger(UrlService.class);

    public CreateUrlResponse saveUrl(CreateUrlRequest request) {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(request.getUrl());
        urlMapping.setCreatedAt(new Date());

        UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);
        savedUrlMapping.setShortCode(Utilities.encodeBase62(savedUrlMapping.getId()));
        savedUrlMapping = urlMappingRepository.save(savedUrlMapping);

        CreateUrlResponse response = new CreateUrlResponse(savedUrlMapping.getId(), savedUrlMapping.getOriginalUrl(), savedUrlMapping.getShortCode());
        return response;
    }

    public UrlMapping getById(Long id) {
        return urlMappingRepository.findById(id).orElseThrow(() -> new UrlNotFoundException(id));
    }

    public List<UrlMappingResponse> getAllUrls() {
        return urlMappingRepository.findAll()
            .stream()
            .map(Utilities::mapToResponse)
            .toList();
    }

    public UrlMapping getByShortCode(String shortCode) {
        return urlMappingRepository.findByShortCode(shortCode).orElseThrow(() -> new UrlNotFoundException("URL not found for short code: " + shortCode));
    }

    public String clicked(String shortCode) {

        String originalUrl = redisService.getOriginalUrl(shortCode);

        if (originalUrl == null) {

            originalUrl = getByShortCode(shortCode).getOriginalUrl();
            redisService.saveUrlMapping(shortCode, originalUrl);
        }

        redisService.incrementClickCount(shortCode);

        return originalUrl;
    }
}
