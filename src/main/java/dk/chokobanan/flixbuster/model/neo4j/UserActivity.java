package dk.chokobanan.flixbuster.model.neo4j;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dk.chokobanan.flixbuster.model.neo4j.Activity;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

@NodeEntity(label = "User")
@Data
public class UserActivity {

    @Id
    @GeneratedValue
    private Long id;

    @Property("user_id")
    private String userId;

    @JsonIgnoreProperties("user")
    @Relationship(type = "WATCHED")
    private List<Activity> watched = new ArrayList<>();

    public UserActivity() {
    }

    public UserActivity(String userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Activity> getWatched() {
        return watched;
    }

    public void setWatched(List<Activity> watched) {
        this.watched = watched;
    }
}
