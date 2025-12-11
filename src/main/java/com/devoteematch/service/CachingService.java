package main.java.com.devoteematch.service;

import main.java.com.devoteematch.model.ContentItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CachingService {

    private static final String RECOMM_KEY_PREFIX = "rec:user:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void cacheRecommendations(String userId, List<ContentItem> items) {
        String key = RECOMM_KEY_PREFIX + userId;
        redisTemplate.opsForValue().set(key, items, 10, TimeUnit.MINUTES);
    }

    @SuppressWarnings("unchecked")
    public List<ContentItem> getCachedRecommendations(String userId) {
        String key = RECOMM_KEY_PREFIX + userId;
        return (List<ContentItem>) redisTemplate.opsForValue().get(key);
    }
}
