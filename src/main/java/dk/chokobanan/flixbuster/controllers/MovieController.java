package dk.chokobanan.flixbuster.controllers;

import dk.chokobanan.flixbuster.model.neo4j.Movie;
import dk.chokobanan.flixbuster.model.neo4j.UserActivity;
import dk.chokobanan.flixbuster.model.postgresql.User;
import dk.chokobanan.flixbuster.repository.neo4j.GenreRepository;
import dk.chokobanan.flixbuster.repository.neo4j.MovieRepository;
import dk.chokobanan.flixbuster.repository.neo4j.UserActivityRepository;
import dk.chokobanan.flixbuster.repository.postgresql.AccountRepository;
import dk.chokobanan.flixbuster.repository.postgresql.UserRepository;
import dk.chokobanan.flixbuster.repository.redis.SessionManagement;
import dk.chokobanan.flixbuster.repository.redis.SessionManagementImpl;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/flixbuster/movies")
public class MovieController {

    private static MovieRepository movieRepository;
    private static GenreRepository genreRepository;
    private static AccountRepository accountRepository;
    private static UserRepository userRepository;
    private static UserActivityRepository userActivityRepository;
    private static SessionManagement management;

    public MovieController(MovieRepository movieRepository, GenreRepository genreRepository, AccountRepository accountRepository, UserRepository userRepository, UserActivityRepository userActivityRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.userActivityRepository = userActivityRepository;
        this.management = new SessionManagementImpl();
    }

    @GetMapping("/{userId}")
    public Iterable<Movie> findAll( @PathVariable UUID userId, @RequestBody String token) {

        User user = userRepository.findOneByUuid(userId);

        if(user != null && management.hasSession(user.getUuid(), user.getAccount().getUuid(), token)) {
            return movieRepository.findAll();
        }

        return null;
    }


    @GetMapping("/{userId}/search/{title}")
    public Iterable<Movie> findMovieByTitleLike(@PathVariable String title, @PathVariable UUID userId, @RequestBody String token) {

        User user = userRepository.findOneByUuid(userId);

        if(user != null && management.hasSession(user.getUuid(), user.getAccount().getUuid(), token)) {
            return movieRepository.findMovieByTitleLike(title);

        }
        return null;

    }

    @GetMapping("/{userId}/genre/{genre}")
    public Collection<Movie> getMoviesByGenre(@PathVariable String genre, @PathVariable UUID userId, @RequestBody String token) {

        User user = userRepository.findOneByUuid(userId);

        if (user != null && management.hasSession(user.getUuid(), user.getAccount().getUuid(), token)) {
            return genreRepository.getMoviesByGenre(genre);
        }

        return null;

    }

    @GetMapping("/{userId}/newest")
    public List<Movie> newest(@RequestParam(value = "limit", required = false) Integer limit, @PathVariable UUID userId, @RequestBody String token) {

        User user = userRepository.findOneByUuid(userId);

        if (user != null && management.hasSession(user.getUuid(), user.getAccount().getUuid(), token)) {
            return movieRepository.newestMovie(limit == null ? 10 : limit);

        }

        return null;

    }

    @GetMapping("/{userId}/director/{name}")
    public List<Movie> director(@PathVariable String name, @PathVariable UUID userId, @RequestBody String token) {

        User user = userRepository.findOneByUuid(userId);

        if (user != null && management.hasSession(user.getUuid(), user.getAccount().getUuid(), token)) {
            return movieRepository.findMovieByDirector(name);
        }

        return null;
    }

    @GetMapping("/{userId}/actor/{name}")
    public List<Movie> actor(@PathVariable String name, @PathVariable UUID userId, @RequestBody String token) {
        User user = userRepository.findOneByUuid(userId);

        if (user != null && management.hasSession(user.getUuid(), user.getAccount().getUuid(), token)) {
            return movieRepository.findMovieByActor(name);
        }

        return null;
    }

    @PostMapping("/{userId}/watched/{title}")
    public UserActivity watchedMovie(@PathVariable UUID userId, @PathVariable String title, @RequestParam int rating, @RequestBody String token) {

        User user = userRepository.findOneByUuid(userId);
        Movie movie = movieRepository.getMovieByTitle(title);

        if(user != null && movie != null && management.hasSession(user.getUuid(), user.getAccount().getUuid(), token)) {

            UserActivity userActivity = userActivityRepository.getUserByUserId(user.getUuid());

            if (userActivity == null) {
                UserActivity newUserActivity = new UserActivity(userId.toString());
                userActivityRepository.save(newUserActivity);
            }

            userActivityRepository.createActivity(user.getUuid(), movie.getTitle(), rating);
            userActivity = userActivityRepository.getUserByUserId(user.getUuid());
            return userActivity;
        }

        return null;
    }





}
