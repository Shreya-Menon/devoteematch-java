package main.java.com.devoteematch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Map;

@Document(collection = "user_profiles")
public class UserProfile {
    @Id
    private String userId;
    private Map<String, Integer> tagPreferences; // tag -> count
    // getters/setters
}
