package org.neo4j.example.movies.test.movies.hateoas;

import org.neo4j.example.movies.test.movies.MoviesAppTest;
import org.neo4j.example.movies.test.movies.MoviesClient;

/**
 * @author Frantisek Hartman
 */
public class RestResourceMoviesAppTest extends MoviesAppTest {

    @Override
    protected MoviesClient createClient() {
        return new RestResourceMoviesClient();
    }
}
