package movies.spring.data.neo4j.repositories;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.domain.Review;
import movies.spring.data.neo4j.domain.Role;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "movies", path = "movies")
public interface MovieRepository extends Neo4jRepository<Movie, Long> {

	@Query("MATCH (m:Movie)<-[r:ACTED_IN]-(a:Person) RETURN m,r,a LIMIT {limit}")
	Collection<Movie> graph(@Param("limit") int limit);
	/*
	 * 查询电影详情
	 * http://localhost:8080/movie/findByTitle?title=The Birdcage
	 */
	@Query("MATCH (m:Movie) Where m.title={title} RETURN m")
	Movie findByTitle(@Param("title") String title);

	/*
	 * 模糊名查询电影详情
	 * http://localhost:8080/movie/findByTitleLike?title=Matrix
	 */
	@Query("MATCH (m:Movie) Where m.title=~{title} RETURN m")
	Collection<Movie> findByTitleLike(@Param("title") String title);

	/*
	 * 查询电影角色名
	 * http://localhost:8080/movie/findRoleByTitle?title=The Birdcage
	 */
	@Query("MATCH (m:Movie)<-[r:ACTED_IN]-(a:Person) WHERE m.title={title} RETURN r.roles")
	ArrayList findRoleByTitle(@Param("title") String title);

	/*
	 * 查询电影评论
	 * http://localhost:8080/movie/findSummaryByTitle?title=The Birdcage
	 */
	@Query("MATCH (m:Movie)<-[r:REVIEWED]-(a:Person) Where m.title={title} RETURN r.summary")
	List<String> findSummaryByTitle(@Param("title") String title);

	/*
	 * 查询电影评分
	 * http://localhost:8080/movie/findRatingByTitle?title=The Birdcage
	 */
	@Query("MATCH (m:Movie)<-[r:REVIEWED]-(a:Person) Where m.title={title} RETURN r.rating")
	List<Long> findRatingByTitle(@Param("title") String title);
}