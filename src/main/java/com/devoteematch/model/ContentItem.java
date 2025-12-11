package main.java.com.devoteematch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "content_items")
public class ContentItem {
    @Id
    private String id;
    private String title;
    private String description;
    private List<String> tags; // e.g., ["bhajan","temple","vrata"]
    private String contentType; // article, video, event
    private double popularityScore; // precomputed metric
    // getters/setters (or Lombok)
}
