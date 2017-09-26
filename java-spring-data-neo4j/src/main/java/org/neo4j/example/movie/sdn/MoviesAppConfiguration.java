package org.neo4j.example.movie.sdn;

import org.neo4j.example.movie.sdn.domain.Movie;
import org.neo4j.example.movie.sdn.repository.MovieRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import org.neo4j.ogm.session.SessionFactory;

/**
 * @author Frantisek Hartman
 */
@Configuration
@EnableWebMvc
@ComponentScan
@EnableNeo4jRepositories(basePackageClasses = MovieRepository.class)
public class MoviesAppConfiguration {

    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        return new org.neo4j.ogm.config.Configuration.Builder()
                .uri("bolt://localhost")
                .build();
    }
    @Bean
    public SessionFactory sessionFactory() {
        return new SessionFactory(configuration(), Movie.class.getPackage().getName());
    }

    @Bean
    public Neo4jTransactionManager transactionManager() {
        return new Neo4jTransactionManager();
    }
}
