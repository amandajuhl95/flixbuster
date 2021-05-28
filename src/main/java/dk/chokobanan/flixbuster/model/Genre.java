package dk.chokobanan.flixbuster.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@Data
@NodeEntity
public class Genre {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @JsonIgnoreProperties("genre")
    @Relationship(type = "IN_GENRE", direction = INCOMING)
    private List<Movie> movies = new ArrayList<>();


    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }
}
