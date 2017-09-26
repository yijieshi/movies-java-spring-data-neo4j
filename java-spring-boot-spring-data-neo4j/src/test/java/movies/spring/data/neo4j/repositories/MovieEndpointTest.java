package movies.spring.data.neo4j.repositories;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.example.movie.domain.Movie;
import org.neo4j.example.movie.domain.Person;
import org.neo4j.example.movie.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import org.neo4j.ogm.session.Session;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author pdtyreus
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class MovieEndpointTest {

	@Autowired
	private Session session;

	@Autowired
	private MovieRepository instance;

	@Autowired
	private PersonRepository personRepository;

	public MovieEndpointTest() {
	}

	@Before
	public void setUp() {
		session.purgeDatabase();

		Movie matrix = new Movie("The Matrix", 1999);

		instance.save(matrix);

		Person keanu = new Person("Keanu Reeves");

		personRepository.save(keanu);

		Role neo = new Role(matrix, keanu);
		neo.addRoleName("Neo");

		matrix.addRole(neo);

		instance.save(matrix);
	}

	@After
	public void tearDown() {
		session.purgeDatabase();
	}

	/**
	 * Test of findByTitle method, of class MovieRepository.
	 */
	@Test
	public void testFindByTitle() {

		String title = "The Matrix";
		Movie result = instance.findByTitle(title);
		assertNotNull(result);
		assertEquals(1999, result.getReleased());
	}

	/**
	 * Test of findByTitleContaining method, of class MovieRepository.
	 */
	@Test
	public void testFindByTitleContaining() {
		String title = "*Matrix*";
		Collection<Movie> result = instance.findByTitleLike(title);
		assertNotNull(result);
		assertEquals(1, result.size());
	}

	/**
	 * Test of graph method, of class MovieRepository.
	 */
	@Test
	public void testGraph() {
		Collection<Movie> graph = instance.graph(5);

		assertEquals(1, graph.size());

		Movie movie = graph.iterator().next();

		assertEquals(1, movie.getRoles().size());

		assertEquals("The Matrix", movie.getTitle());
		assertEquals("Keanu Reeves", movie.getRoles().iterator().next().getPerson().getName());
	}
}
