package org.neo4j.example.movie.sdn.repository;

import org.neo4j.example.movie.sdn.domain.Person;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author Frantisek Hartman
 */
public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Person findByName(String name, @Depth int depth);

}
