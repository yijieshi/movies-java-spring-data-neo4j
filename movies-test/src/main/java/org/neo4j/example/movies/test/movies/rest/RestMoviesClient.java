package org.neo4j.example.movies.test.movies.rest;

import java.util.Arrays;
import java.util.List;

import org.neo4j.example.movie.domain.Movie;
import org.neo4j.example.movie.domain.Person;
import org.neo4j.example.movies.test.movies.MoviesClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author Frantisek Hartman
 */
public class RestMoviesClient implements MoviesClient {

    private RestTemplate template = new RestTemplate();

    private ParameterizedTypeReference<List<Movie>> movieListType = new ParameterizedTypeReference<List<Movie>>() {};

    @Override
    public Movie findMovie(Long id) {
        ResponseEntity<Movie> response = template.getForEntity("http://localhost:8080/movies/{id}",
                Movie.class,
                id);

        return response.getBody();
    }

    @Override
    public Movie findMovie(String title) {
        ResponseEntity<Movie> response = template.getForEntity("http://localhost:8080/movies/search?title={title}",
                Movie.class,
                title);

        return response.getBody();
    }

    @Override
    public List<Movie> findMovies(int page, int size) {
        ResponseEntity<Movie[]> response = template.getForEntity("http://localhost:8080/movies?page={page}&size={size}",
                Movie[].class,
                page,
                size);

        return Arrays.asList(response.getBody());
    }

    @Override
    public Movie createMovie(Movie movie) {
        ResponseEntity<Movie> response = template.postForEntity("http://localhost:8080/movies", movie, Movie.class);
        return response.getBody();
    }

    @Override
    public void updateMovie(Movie movie) {
        template.put("http://localhost:8080/movies", movie, Movie.class);
    }

    @Override
    public void deleteMovie(Movie movie) {
        template.delete("http://localhost:8080/movies/{id}", movie.getId());
    }

    @Override
    public Person findPerson(Long id) {
        ResponseEntity<Person> response = template.getForEntity("http://localhost:8080/people/{id}",
                Person.class,
                id);

        return response.getBody();
    }

    @Override
    public Person findPerson(String name) {
        ResponseEntity<Person> response = template.getForEntity("http://localhost:8080/people/search?name={name}",
                Person.class,
                name);

        return response.getBody();
    }

    @Override
    public List<Person> findPeople(int page, int size) {
        ResponseEntity<Person[]> response = template.getForEntity("http://localhost:8080/people?page={page}&size={size}",
                Person[].class,
                page,
                size);

        return Arrays.asList(response.getBody());
    }

    @Override
    public Person createPerson(Person person) {
        ResponseEntity<Person> response = template.postForEntity("http://localhost:8080/people", person, Person.class);
        return response.getBody();
    }

    @Override
    public void updatePerson(Person person) {
        template.put("http://localhost:8080/people", person, Person.class);
    }

    @Override
    public void deletePerson(Person person) {
        template.delete("http://localhost:8080/people/{id}", person.getId());
    }
}
