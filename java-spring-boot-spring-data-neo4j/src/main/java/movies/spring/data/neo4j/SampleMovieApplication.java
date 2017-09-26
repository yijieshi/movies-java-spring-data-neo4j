package movies.spring.data.neo4j;

import movies.spring.data.neo4j.repositories.MovieRepository;
import org.neo4j.example.movie.domain.Movie;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

/**
 * @author Michael Hunger
 * @author Mark Angrish
 */
@SpringBootApplication
@EntityScan(basePackageClasses = Movie.class)
//@EnableNeo4jRepositories(basePackageClasses = MovieRepository.class)
public class SampleMovieApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleMovieApplication.class, args);
	}
}
