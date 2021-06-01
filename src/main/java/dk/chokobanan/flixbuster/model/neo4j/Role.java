package dk.chokobanan.flixbuster.model.neo4j;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dk.chokobanan.flixbuster.model.neo4j.Movie;
import dk.chokobanan.flixbuster.model.neo4j.Person;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Data
@RelationshipEntity(type = "ACTED_IN")
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    private List<String> roles = new ArrayList<>();

    @StartNode
    @JsonIgnoreProperties({"actedIn", "directed"})
    private Person person;

    @EndNode
    @JsonIgnoreProperties({"actors", "directors"})
    private Movie movie;
}
