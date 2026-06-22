package com.shylesh.urlshortener.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;

import com.shylesh.urlshortener.entity.UrlMapping;
import com.shylesh.urlshortener.repository.UrlMappingRepository;


@Service
public class AnalyticsFlushService {

    Logger logger = org.slf4j.LoggerFactory.getLogger(AnalyticsFlushService.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @Scheduled(fixedRate = 120000)
    public void flushClickCounts() {

        logger.info("Flushing click counts from Redis to Database...");
        Set<String> shortCodes = redisService.getActiveCounters();

        for(String shortCode : shortCodes) {

            long pendingClicks = redisService.consumePendingClicks(shortCode);

            if(pendingClicks == 0) {
                continue;
            }

            UrlMapping urlMapping = urlMappingRepository.findByShortCode(shortCode)
                            .orElseThrow(() -> new RuntimeException("UrlMapping not found for shortCode: " + shortCode));

            urlMapping.setClickCount(urlMapping.getClickCount() + pendingClicks);

            urlMappingRepository.save(urlMapping);

            }

    }
}
