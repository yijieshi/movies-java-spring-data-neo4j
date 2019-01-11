package movies.spring.data.neo4j.repositories;

import movies.spring.data.neo4j.domain.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pdtyreus
 * @author Mark Angrish
 */
public interface PersonRepository extends Neo4jRepository<Person, Long> {
    /*
     * 查询明星详情
     * http://localhost:8080/person/findByName?name=Joel Silver
     */
    @Query("MATCH (p:Person) Where p.name={name} RETURN p")
    Person findByName(@Param("name") String name);
    /*
     * 查询明星在电影的角色名
     * http://localhost:8080/person/findMovieByName?name=Charlize Theron&title=That Thing You Do
     */
    @Query("MATCH (m:Movie)<-[r:ACTED_IN]-(a:Person) WHERE a.name={name} and m.title={title} RETURN r.roles")
    ArrayList findRoleByNameAndTitle(@Param("name") String name, @Param("title") String title);
}