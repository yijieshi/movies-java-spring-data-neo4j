package org.neo4j.example.movie.sdn.controller;

import java.util.List;

import org.neo4j.example.movie.sdn.domain.Movie;
import org.neo4j.example.movie.sdn.service.MovieService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Frantisek Hartman
 */
@RestController
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies/{id}")
    public Movie findById(@PathVariable("id") Long id) {
        return movieService.findById(id);
    }

    @GetMapping("/movies/search")
    public Movie findByTitle(@RequestParam("title") String title) {
        return movieService.findByTitle(title);
    }

    @GetMapping("/movies")
    public List<Movie> findMovies(@RequestParam("page") int page, @RequestParam("size") int size) {
        return movieService.findAll(page, size);
    }

    @PostMapping("/movies")
    public Movie createMovie(@RequestBody Movie movie) {
        return movieService.createMovie(movie);
    }

    @PutMapping("/movies")
    public void updateMovie(@RequestBody Movie movie) {
        movieService.updateMovie(movie);
    }

    @DeleteMapping("/movies/{id}")
    public void deleteMovie(@PathVariable("id") Long id) {
        movieService.delete(id);
    }
}
