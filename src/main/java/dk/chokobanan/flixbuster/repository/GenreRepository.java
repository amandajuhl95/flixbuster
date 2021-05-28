package dk.chokobanan.flixbuster.repository;

import dk.chokobanan.flixbuster.model.Genre;
import dk.chokobanan.flixbuster.model.Movie;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GenreRepository extends Neo4jRepository<Genre, Long> {
    Genre getGenreByName(String name);

    Iterable<Genre> findGenreByNameLike(String name);

    @Query("MATCH (g:Genre {name:$genre})-[ig:IN_GENRE]-(MOVIE) -[r]-(e) RETURN collect(ig), collect(g), collect(r), collect(e), MOVIE")
    List<Movie> getMoviesByGenre(@Param("genre") String name);
}