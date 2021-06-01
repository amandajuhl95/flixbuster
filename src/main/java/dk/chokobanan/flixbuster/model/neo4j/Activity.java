package dk.chokobanan.flixbuster.model.neo4j;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

@Data
@RelationshipEntity(type = "WATCHED")
public class Activity {
    @Id
    @GeneratedValue
    private Long id;

    private int rating;

    @StartNode
    @JsonIgnoreProperties({"watched"})
    private UserActivity user;

    @EndNode
    private Movie movie;
}