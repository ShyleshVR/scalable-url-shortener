package com.shylesh.urlshortener.controller;

import com.shylesh.urlshortener.service.UrlService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "URL Shortener API", description = "API for URL shortening service")
@RestController
public class UrlController {

    @Autowired
    private UrlService urlService;


    @Operation(summary = "Create a new shortened URL", description = "Creates a new shortened URL for the given original URL.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully created shortened URL"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/api/urls")
    public CreateUrlResponse createUrl(@Valid @RequestBody CreateUrlRequest request) {
        return urlService.saveUrl(request);
    }



    @Operation(summary = "Get a shortened URL by ID", description = "Retrieves the shortened URL mapping for the given ID.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved URL mapping"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "URL mapping not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/api/urls/{id}")
    public UrlMappingResponse getById(@PathVariable Long id) {
        return new UrlMappingResponse(urlService.getById(id));
    }



    @Operation(summary = "Get all shortened URLs", description = "Retrieves all shortened URL mappings.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved all URL mappings"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/api/urls/all")
    public List<UrlMappingResponse> getAllUrls() {
        return urlService.getAllUrls();
    }
    

    @Operation(summary = "Get a shortened URL by short code", description = "Retrieves the shortened URL mapping for the given short code.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved URL mapping"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "URL mapping not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/api/urls/shortcode/{shortCode}")
    public UrlMappingResponse getByShortCode(@PathVariable String shortCode) {
        return new UrlMappingResponse(urlService.getByShortCode(shortCode));
    }



    @Operation(summary = "Redirect to the original URL", description = "Redirects the request to the original URL for the given short code.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "302", description = "Redirected to original URL"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Short code not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> getMethodName(@PathVariable String shortCode) {
        return ResponseEntity.status(HttpStatusCode.valueOf(302)).header("Location", urlService.clicked(shortCode)).build();
    }
    
    
}
