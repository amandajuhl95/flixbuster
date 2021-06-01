package dk.chokobanan.flixbuster.model.neo4j;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleased() {
        return released;
    }

    public void setReleased(int released) {
        this.released = released;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCertification() {
        return certification;
    }

    public void setCertification(int certification) {
        this.certification = certification;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public List<Role> getActors() {
        return actors;
    }

    public void setActors(List<Role> actors) {
        this.actors = actors;
    }

    public List<Person> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Person> directors) {
        this.directors = directors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
