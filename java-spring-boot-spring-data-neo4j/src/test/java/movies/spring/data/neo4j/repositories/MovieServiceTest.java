package movies.spring.data.neo4j.repositories;

import movies.spring.data.neo4j.services.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.example.movie.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import org.neo4j.ogm.session.Session;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Frantisek Hartman
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieServiceTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MovieService movieService;

    @Before
    public void setUp() throws Exception {
        movieRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    public void createAndLoadMovie() throws Exception {
        Movie movie = movieService.createMovie("The Matrix", 1999);
        assertThat(movie).isNotNull();

        Movie loaded = movieService.findById(movie.getId());
        assertThat(loaded.getTitle()).isEqualTo("The Matrix");
        assertThat(loaded.getReleased()).isEqualTo(1999);
    }
}
