package org.neo4j.example.movies.test.movies;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.example.movie.domain.Movie;
import org.neo4j.example.movie.domain.Person;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * @author Frantisek Hartman
 */
public abstract class MoviesAppTest {

    private static String moviesCql;

    private RestTemplate restTemplate = new RestTemplate();
    private MoviesClient client;

    @BeforeClass
    public static void setUpClass() throws Exception {
        try (InputStream in = MoviesAppTest.class.getResourceAsStream("/movies.cql")) {
            moviesCql = IOUtils.toString(in, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract MoviesClient createClient();

    @Before
    public void setUp() throws Exception {
        prepareDatabase();
        client = createClient();

        List<Movie> movies = client.findMovies(0, 100);
        assertThat(movies).hasSize(38);
    }

    private void prepareDatabase() {

        Driver driver = GraphDatabase.driver("bolt://localhost");
        try (Session session = driver.session()) {

            try (Transaction tx = session.beginTransaction()) {

                tx.run("MATCH (n) DETACH DELETE (n) ");

                tx.run(moviesCql);

                tx.success();
            }
        }

    }

    @Test
    public void findMovieByTitle() throws Exception {
        Movie movie = client.findMovie("The Matrix");

        assertThat(movie.getId()).isNotNull();
        assertThat(movie.getTitle()).isEqualTo("The Matrix");
        assertThat(movie.getReleased()).isEqualTo(1999);
        assertThat(movie.getTagline()).isEqualTo("Welcome to the Real World");
    }

    @Test
    public void findById() throws Exception {
        // need to find out id somehow
        Movie matrix = client.findMovie("The Matrix");

        Movie movie = client.findMovie(matrix.getId());

        assertThat(movie.getId()).isEqualTo(matrix.getId());
        assertThat(movie.getTitle()).isEqualTo("The Matrix");
        assertThat(movie.getReleased()).isEqualTo(1999);
        assertThat(movie.getTagline()).isEqualTo("Welcome to the Real World");
    }

    @Test
    public void createMovie() throws Exception {
        Movie movie = new Movie("The Imitation Game", 2014);
        movie.setTagline("The true enigma was the man who cracked the code");

        movie = client.createMovie(movie);

        assertThat(movie.getId()).isNotNull();
        assertThat(movie.getTitle()).isEqualTo("The Imitation Game");
        assertThat(movie.getReleased()).isEqualTo(2014);
        assertThat(movie.getTagline()).isEqualTo("The true enigma was the man who cracked the code");
    }

    @Test
    public void updateAndloadMovie() throws Exception {
        Movie matrix = client.findMovie("The Matrix");
        matrix.setTagline("You take the red pill, you stay in Wonderland, and I show you how deep the rabbit hole goes.");

        client.updateMovie(matrix);

        Movie updated = client.findMovie(matrix.getId());
        assertThat(updated.getTagline())
                .isEqualTo("You take the red pill, you stay in Wonderland, and I show you how deep the rabbit hole goes.");
    }

    @Test
    public void loadMovies() throws Exception {
        List<Movie> movies = client.findMovies(0, 100);

        assertThat(movies).extracting(m -> tuple(m.getTitle(), m.getReleased()))
        .contains(
                tuple("The Matrix", 1999),
                tuple("The Matrix Reloaded", 2003),
                tuple("The Matrix Revolutions", 2003)
        );
    }

    @Test
    public void deleteMovie() throws Exception {
        Movie matrix = client.findMovie("The Matrix");

        client.deleteMovie(matrix);

        Movie movie = client.findMovie(matrix.getId());
        assertThat(movie).isNull();
    }

    @Test
    public void findPersonByTitle() throws Exception {
        Person movie = client.findPerson("Keanu Reeves");

        assertThat(movie.getId()).isNotNull();
        assertThat(movie.getName()).isEqualTo("Keanu Reeves");
        assertThat(movie.getBorn()).isEqualTo(1964);
    }

    @Test
    public void findPersonById() throws Exception {
        Person keanu = client.findPerson("Keanu Reeves");

        Person movie = client.findPerson(keanu.getId());

        assertThat(movie.getId()).isNotNull();
        assertThat(movie.getName()).isEqualTo("Keanu Reeves");
        assertThat(movie.getBorn()).isEqualTo(1964);
    }

    @Test
    public void findPeople() throws Exception {
        List<Person> people = client.findPeople(0, 100);

        assertThat(people).extracting(p -> tuple(p.getName(), p.getBorn()))
        .contains(
                tuple("Keanu Reeves", 1964),
                tuple("Carrie-Anne Moss", 1967),
                tuple("Laurence Fishburne", 1961),
                tuple("Hugo Weaving", 1960)
        );
    }

    @Test
    public void createPerson() throws Exception {
        Person person = new Person("Benedict Cumberbatch");
        person.setBorn(1976);
        Person cumberbatch = client.createPerson(person);

        assertThat(cumberbatch.getId()).isNotNull();
        assertThat(cumberbatch.getName()).isEqualTo("Benedict Cumberbatch");
        assertThat(cumberbatch.getBorn()).isEqualTo(1976);
    }

    @Test
    public void updatePerson() throws Exception {
        Person keanu = client.findPerson("Keanu Reeves");
        keanu.setBorn(1897);

        client.updatePerson(keanu);

        Person person = client.findPerson(keanu.getId());
        assertThat(person.getBorn()).isEqualTo(1897);
    }

    @Test
    public void deletePerson() throws Exception {
        Person keanu = client.findPerson("Keanu Reeves");

        client.deletePerson(keanu);

        Person person = client.findPerson(keanu.getId());
        assertThat(person).isNull();
    }
}
