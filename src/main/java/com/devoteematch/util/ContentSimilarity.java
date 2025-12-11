package main.java.com.devoteematch.util;

import main.java.com.devoteematch.model.ContentItem;
import java.util.*;
import java.util.stream.Collectors;

public class ContentSimilarity {

    // Simple tag overlap score
    public static double score(ContentItem item, Map<String,Integer> preferences) {
        if (item.getTags() == null || item.getTags().isEmpty()) return 0.0;
        double score = 0.0;
        for (String tag : item.getTags()) {
            score += preferences.getOrDefault(tag, 0);
        }
        // combine with popularity
        return score + item.getPopularityScore() * 0.1;
    }

    public static List<ContentItem> rankItems(List<ContentItem> items, Map<String,Integer> prefs) {
        return items.stream()
                .sorted(Comparator.comparingDouble((ContentItem it) -> score(it, prefs)).reversed())
                .collect(Collectors.toList());
    }
}
