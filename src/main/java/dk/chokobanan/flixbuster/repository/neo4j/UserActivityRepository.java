package dk.chokobanan.flixbuster.repository.neo4j;


import dk.chokobanan.flixbuster.model.neo4j.Activity;
import dk.chokobanan.flixbuster.model.neo4j.UserActivity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserActivityRepository extends Neo4jRepository<UserActivity, Long> {

    UserActivity getUserByUserId(UUID userId);

    @Query("MATCH (m:Movie),(u:User) WHERE u.user_id = $user_id AND m.title = $title MERGE (u)-[w:WATCHED {rating:$rating}]->(m) RETURN w")
    Activity createActivity(@Param("user_id") UUID userId, @Param("title") String title, @Param("rating") int rating);

}
