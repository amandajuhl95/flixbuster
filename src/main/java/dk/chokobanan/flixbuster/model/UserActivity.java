package dk.chokobanan.flixbuster.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

@NodeEntity(label = "User")
@Data
public class UserActivity {

    @Id
    private Long id;

    @JsonIgnoreProperties("UserActivity")
    @Relationship(type = "WATCHED")
    private List<Activity> activities = new ArrayList<>();

    public UserActivity() {
    }

    public UserActivity(Long id) {
        this.id = id;
    }
}
