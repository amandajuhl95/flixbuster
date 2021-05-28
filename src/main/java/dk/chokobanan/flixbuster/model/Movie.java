package dk.chokobanan.flixbuster.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;
import static org.neo4j.ogm.annotation.Relationship.OUTGOING;

@Data
@NodeEntity
public class Movie {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private int released;
    private String tagline;
    private String description;
    private int certification;
    @Property("duration_hours")
    private int hours;
    @Property("duration_minutes")
    private int minutes;

    @JsonIgnoreProperties("movie")
    @Relationship(type = "ACTED_IN", direction = INCOMING)
    private List<Role> actors;

    @JsonIgnoreProperties({"actedIn", "directed"})
    @Relationship(type = "DIRECTED", direction = INCOMING)
    private List<Person> directors = new ArrayList<>();

    @JsonIgnoreProperties({"movies"})
    @Relationship(type = "IN_GENRE")
    private List<Genre> genres = new ArrayList<>();

    public Movie() {
    }

    public Movie(String title, int released, String tagline, String description, int certification, int hours, int minutes) {
        this.title = title;
        this.released = released;
        this.tagline = tagline;
        this.description = description;
        this.certification = certification;
        this.hours = hours;
        this.minutes = minutes;

    }
}
