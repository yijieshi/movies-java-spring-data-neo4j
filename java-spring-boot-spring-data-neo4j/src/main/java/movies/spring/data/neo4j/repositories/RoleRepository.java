package movies.spring.data.neo4j.repositories;

import org.neo4j.example.movie.domain.Role;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Frantisek Hartman
 */
//@RepositoryRestResource(collectionResourceRel = "roles", path = "roles")
public interface RoleRepository extends Neo4jRepository<Role, Long> {
}
