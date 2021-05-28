package dk.chokobanan.flixbuster.repository;


import dk.chokobanan.flixbuster.model.Activity;
import dk.chokobanan.flixbuster.model.Movie;
import dk.chokobanan.flixbuster.model.UserActivity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserActivityRepository extends Neo4jRepository<UserActivity, Long> {

    UserActivity getUserActivityById(Long id);

    @Query("MATCH (m:Movie),(u:User) WHERE u.id = $userId AND m.id = $movieId CREATE (u)-[w:WATCHED]->(m) RETURN w")
    Activity createActivity(@Param("userId") Long userId, @Param("movieId") Long movieId);

}
