package dk.chokobanan.flixbuster.repository.neo4j;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import dk.chokobanan.flixbuster.model.neo4j.Movie;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "movies", path = "movies")
public interface MovieRepository extends Neo4jRepository<Movie, Long> {
    Movie getMovieByTitle(String title);

    Movie getMovieById(Long id);

    Iterable<Movie> findMovieByTitleLike(String title);

    @Query("MATCH (m:Movie) RETURN m ORDER BY m.released DESC LIMIT $limit")
    List<Movie> newestMovie(@Param("limit") int limit);

    @Query("MATCH (a:Person {name:$name}) -[ai:ACTED_IN]-(m:Movie)<-[aord]-(p:Person) RETURN m,collect(a),collect(p), collect(ai), collect(aord)")
    List<Movie> findMovieByActor(@Param("name") String name);

    @Query("MATCH (di:Person {name:$name}) -[d:DIRECTED]-(m:Movie)<-[aord]-(p:Person) RETURN m,collect(di),collect(p), collect(d), collect(aord)")
    List<Movie> findMovieByDirector(@Param("name") String name);

}
