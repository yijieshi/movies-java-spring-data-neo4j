package org.neo4j.example.movies.test.movies.rest;

import org.neo4j.example.movies.test.movies.MoviesAppTest;
import org.neo4j.example.movies.test.movies.MoviesClient;

/**
 * @author Frantisek Hartman
 */
public class RestMoviesAppTest extends MoviesAppTest {

    @Override
    protected MoviesClient createClient() {
        return new RestMoviesClient();
    }

}
