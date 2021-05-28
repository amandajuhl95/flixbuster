package dk.chokobanan.flixbuster.controllers;

import org.springframework.web.bind.annotation.*;
import dk.chokobanan.flixbuster.model.*;
import dk.chokobanan.flixbuster.repository.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/flixbuster/movies")
public class MovieController {

    private static MovieRepository movieRepository;
    private static GenreRepository genreRepository;
    private static AccountRepository accountRepository;
    private static UserRepository userRepository;
    private static UserActivityRepository userActivityRepository;

    public MovieController(MovieRepository movieRepository, GenreRepository genreRepository, AccountRepository accountRepository, UserRepository userRepository, UserActivityRepository userActivityRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.userActivityRepository = userActivityRepository;
    }

    @GetMapping
    public Iterable<Movie> findAll() {
        return movieRepository.findAll();
    }

    @GetMapping("/{title}")
    public Movie getMovieByTitle(@PathVariable String title) {
        return movieRepository.getMovieByTitle(title);
    }

    @GetMapping("/search/{title}")
    public Iterable<Movie> findMovieByTitleLike(@PathVariable String title) {
        return movieRepository.findMovieByTitleLike(title);
    }

    @GetMapping("/genre/{genre}")
    public Collection<Movie> getMoviesByGenre(@PathVariable String genre) {
        Collection<Movie> result = genreRepository.getMoviesByGenre(genre);
        return result;
    }

    @GetMapping("/newest")
    public List<Movie> newest(@RequestParam(value = "limit", required = false) Integer limit) {
        List<Movie> result = movieRepository.newestMovie(limit == null ? 10 : limit);
        return result;
    }

    @GetMapping("/director/{name}")
    public List<Movie> director(@PathVariable String name) {
        List<Movie> result = movieRepository.findMovieByDirector(name);
        return result;
    }

    @GetMapping("/actor/{name}")
    public List<Movie> actor(@PathVariable String name) {
        List<Movie> result = movieRepository.findMovieByActor(name);
        return result;
    }

    @PostMapping("/watched/{userId}/{movieId}")
    public Activity watchedMovie(@PathVariable Long userId, @PathVariable Long movieId) {

        User user = userRepository.getOne(userId);
        boolean userExists = userActivityRepository.existsById(user.getId());
        boolean movieExists = movieRepository.existsById(movieId);

        if(!userExists) {
            UserActivity newUserActivity = new UserActivity(user.getId());
            userActivityRepository.save(newUserActivity);
        }

        userExists = userActivityRepository.existsById(user.getId());
        if(userExists && movieExists) {
            Activity activity = userActivityRepository.createActivity(user.getId(), movieId);
            return activity;
        }

        return null;
    }





}
