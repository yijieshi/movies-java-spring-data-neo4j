package org.neo4j.example.movie.sdn.service;

import java.util.List;
import java.util.Optional;

import org.neo4j.example.movie.sdn.domain.Movie;
import org.neo4j.example.movie.sdn.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.domain.PageRequest.of;

/**
 * @author Frantisek Hartman
 */
@Service
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie findById(Long id) {
        Optional<Movie> byId = movieRepository.findById(id, 0);
        return byId.orElse(null);
    }

    public Movie findByTitle(String title) {
        return movieRepository.findByTitle(title, 0);
    }

    public List<Movie> findAll(int page, int size) {
        return movieRepository.findAll(of(page, size), 0).getContent();
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void updateMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public void delete(Long id) {
        movieRepository.deleteById(id);
    }
}
