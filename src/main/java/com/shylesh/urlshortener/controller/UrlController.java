package com.shylesh.urlshortener.controller;

import com.shylesh.urlshortener.service.UrlService;
import com.shylesh.urlshortener.dto.CreateUrlRequest;
import com.shylesh.urlshortener.dto.CreateUrlResponse;
import com.shylesh.urlshortener.entity.UrlMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping
    @RequestMapping("/api/urls")
    public CreateUrlResponse createUrl(@RequestBody CreateUrlRequest request) {
        return urlService.saveUrl(request);
    }

    @GetMapping
    @RequestMapping("/api/urls/{id}")
    public UrlMapping getById(@PathVariable Long id) {
        return urlService.getById(id);
    }
}
