package com.shylesh.urlshortener.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "url_mapping")
public class UrlMapping {
    
    @Id
    long id;
    
    String originalUrl;
    String shortUrl;
    Date createdAt;
}
