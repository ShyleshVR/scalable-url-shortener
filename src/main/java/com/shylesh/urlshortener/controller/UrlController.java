package com.shylesh.urlshortener.controller;

import com.shylesh.urlshortener.service.UrlService;

import jakarta.validation.Valid;

import com.shylesh.urlshortener.dto.CreateUrlRequest;
import com.shylesh.urlshortener.dto.CreateUrlResponse;
import com.shylesh.urlshortener.entity.UrlMapping;
import com.shylesh.urlshortener.dto.UrlMappingResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@RestController
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping
    @RequestMapping("/api/urls")
    public CreateUrlResponse createUrl(@Valid @RequestBody CreateUrlRequest request) {
        return urlService.saveUrl(request);
    }

    @GetMapping
    @RequestMapping("/api/urls/{id}")
    public UrlMappingResponse getById(@PathVariable Long id) {
        return new UrlMappingResponse(urlService.getById(id));
    }

    @GetMapping("/api/urls/all")
    public List<UrlMappingResponse> getAllUrls() {
        return urlService.getAllUrls();
    }
    
    @GetMapping("/api/urls/shortcode/{shortCode}")
    public UrlMappingResponse getByShortCode(@PathVariable String shortCode) {
        return new UrlMappingResponse(urlService.getByShortCode(shortCode));
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> getMethodName(@PathVariable String shortCode) {
        return ResponseEntity.status(HttpStatusCode.valueOf(302)).header("Location", urlService.clicked(shortCode).getOriginalUrl()).build();
    }
    
    
}
