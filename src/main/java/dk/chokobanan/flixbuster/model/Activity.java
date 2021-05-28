package dk.chokobanan.flixbuster.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

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