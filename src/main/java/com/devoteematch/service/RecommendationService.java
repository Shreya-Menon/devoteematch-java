package main.java.com.devoteematch.service;

import main.java.com.devoteematch.model.ContentItem;
import main.java.com.devoteematch.model.UserProfile;
import main.java.com.devoteematch.repository.ContentRepository;
import main.java.com.devoteematch.repository.UserProfileRepository;
import main.java.com.devoteematch.util.ContentSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RecommendationService {

    @Autowired
    private ContentRepository contentRepo;

    @Autowired
    private UserProfileRepository profileRepo;

    @Autowired
    private CachingService cache;

    public List<ContentItem> recommendForUser(String userId, int limit) {
        // 1. check cache
        List<ContentItem> cached = cache.getCachedRecommendations(userId);
        if (cached != null && !cached.isEmpty()) return cached;

        // 2. fetch profile
        Optional<UserProfile> profileOpt = profileRepo.findById(userId);
        Map<String, Integer> prefs = profileOpt.map(UserProfile::getTagPreferences).orElseGet(HashMap::new);

        // 3. fetch candidate contents (top popular and tag-based)
        List<String> topTags = new ArrayList<>(prefs.keySet());
        List<ContentItem> candidates = new ArrayList<>();
        if (!topTags.isEmpty()) candidates.addAll(contentRepo.findByTagsIn(topTags));
        candidates.addAll(contentRepo.findTop50ByOrderByPopularityScoreDesc());

        // dedupe
        Map<String, ContentItem> map = new LinkedHashMap<>();
        for (ContentItem c : candidates) map.put(c.getId(), c);

        List<ContentItem> ranked = ContentSimilarity.rankItems(new ArrayList<>(map.values()), prefs);
        List<ContentItem> result = ranked.subList(0, Math.min(limit, ranked.size()));

        // 4. cache
        cache.cacheRecommendations(userId, result);

        return result;
    }
}
