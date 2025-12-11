package main.java.com.devoteematch.repository;

import com.devoteematch.model.ContentItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ContentRepository extends MongoRepository<ContentItem, String> {
    List<ContentItem> findByTagsIn(List<String> tags);
    List<ContentItem> findTop50ByOrderByPopularityScoreDesc();
}
