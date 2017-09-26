package org.neo4j.example.movies.test.movies;

import java.util.List;

import org.neo4j.example.movie.domain.Movie;
import org.neo4j.example.movie.domain.Person;


/**
 * @author Frantisek Hartman
 */
public interface MoviesClient {

    // -- Movie

    Movie findMovie(Long id);

    Movie findMovie(String title);

    List<Movie> findMovies(int page, int size);

    Movie createMovie(Movie movie);

    void updateMovie(Movie movie);

    void deleteMovie(Movie movie);

    // -- Person

    Person findPerson(Long id);

    Person findPerson(String name);

    List<Person> findPeople(int page, int size);

    Person createPerson(Person person);

    void updatePerson(Person person);

    void deletePerson(Person person);

}
