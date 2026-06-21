package com.shylesh.urlshortener.service;

import java.lang.foreign.Linker.Option;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shylesh.urlshortener.dto.CreateUrlRequest;
import com.shylesh.urlshortener.dto.CreateUrlResponse;
import com.shylesh.urlshortener.entity.UrlMapping;
import com.shylesh.urlshortener.repository.UrlMappingRepository;
import com.shylesh.urlshortener.exception.UrlNotFoundException;

@Service
public class UrlService {

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    public CreateUrlResponse saveUrl(CreateUrlRequest request) {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(request.getUrl());
        urlMapping.setShortUrl("shortUrl");
        urlMapping.setCreatedAt(new java.util.Date());

        UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);
        CreateUrlResponse response = new CreateUrlResponse(savedUrlMapping.getId(), savedUrlMapping.getOriginalUrl(), savedUrlMapping.getShortUrl());
        return response;
    }


    public UrlMapping getById(Long id) {
        return urlMappingRepository.findById(id).orElseThrow(() -> new UrlNotFoundException(id));
    }

}
