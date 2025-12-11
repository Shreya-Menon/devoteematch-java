package main.java.com.devoteematch.controller;

import main.java.com.devoteematch.model.ContentItem;
import main.java.com.devoteematch.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService service;

    @GetMapping("/{userId}")
    public List<ContentItem> getRecommendations(@PathVariable String userId,
                                               @RequestParam(defaultValue = "10") int limit) {
        return service.recommendForUser(userId, limit);
    }
}
