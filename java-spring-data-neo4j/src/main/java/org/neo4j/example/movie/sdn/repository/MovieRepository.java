package org.neo4j.example.movie.sdn.repository;

import org.neo4j.example.movie.sdn.domain.Movie;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author Frantisek Hartman
 */
public interface MovieRepository extends Neo4jRepository<Movie, Long> {

    Movie findByTitle(String title, @Depth int depth);
}
