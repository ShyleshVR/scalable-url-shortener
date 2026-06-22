package com.shylesh.urlshortener.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.Collections;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Service
public class RedisService {

    private static final String URL_PREFIX = "url:";
    private static final String CLICK_PREFIX = "clicks:";
    private static final String ACTIVE_COUNTERS_KEY = "active_click_counters";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    Logger logger = org.slf4j.LoggerFactory.getLogger(RedisService.class);

    public void saveUrlMapping(String shortCode, String originalUrl) {

        redisTemplate.opsForValue().set(URL_PREFIX + shortCode, originalUrl, Duration.ofHours(24));
        logger.info("Saved URL mapping in Redis: {} -> {}", shortCode, originalUrl);
    }

    public String getOriginalUrl(String shortCode) {
        logger.info("Searching for short code in Redis: {}", shortCode);

        String originalUrl = redisTemplate.opsForValue().get(URL_PREFIX + shortCode);
        if (originalUrl != null) {
            logger.info("Cache hit for short code: {}", shortCode);
        } else {
            logger.info("Cache miss for short code: {}", shortCode);
        }
        return originalUrl;
    }

    public void evictUrl(String shortCode) {

        redisTemplate.delete(URL_PREFIX + shortCode);

        logger.info("Evicted URL cache for short code {}", shortCode);
    }

        /*
     * CLICK ANALYTICS OPERATIONS
     */

    public void incrementClickCount(String shortCode) {

        redisTemplate.opsForValue().increment(CLICK_PREFIX + shortCode);

        redisTemplate.opsForSet().add(ACTIVE_COUNTERS_KEY, shortCode);
    }

    public long getPendingClicks(String shortCode) {

        String value = redisTemplate.opsForValue().get(CLICK_PREFIX + shortCode);

        if (value == null) {
            return 0L;
        }

        return Long.parseLong(value);
    }

    public Set<String> getActiveCounters() {

        Set<String> counters = redisTemplate.opsForSet().members(ACTIVE_COUNTERS_KEY);

        return counters == null ? Collections.emptySet() : counters;
    }

    public void clearPendingClicks(String shortCode) {

        redisTemplate.delete(CLICK_PREFIX + shortCode);

        redisTemplate.opsForSet().remove(ACTIVE_COUNTERS_KEY, shortCode);

        logger.info("Cleared pending clicks for {}", shortCode);
    }

    public long consumePendingClicks(String shortCode) {

        String key = CLICK_PREFIX + shortCode;

        String previousValue = redisTemplate.opsForValue().getAndSet(key, "0");

        if (previousValue == null) {
            return 0L;
        }

        return Long.parseLong(previousValue);
     
    }

    /*
     * ANALYTICS HELPER
     */

    public long getTotalClicks(String shortCode, long databaseClickCount) {

        return databaseClickCount + getPendingClicks(shortCode);
    }
}
