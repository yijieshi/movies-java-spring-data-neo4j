package movies.spring.data.neo4j.services;

import java.util.*;

import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.domain.Review;
import movies.spring.data.neo4j.domain.Role;
import movies.spring.data.neo4j.repositories.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.neo4j.ogm.session.Utils.map;

@Service
public class MovieService {

    private final static Logger LOG = LoggerFactory.getLogger(MovieService.class);

	private final MovieRepository movieRepository;
	public MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

    private Map<String, Object> toD3Format(Collection<Movie> movies) {
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> rels = new ArrayList<>();
        int i = 0;
        Iterator<Movie> result = movies.iterator();
        while (result.hasNext()) {
            Movie movie = result.next();
            nodes.add(map("title", movie.getTitle(), "label", "movie"));
            int target = i;
            i++;
            for (Role role : movie.getRoles()) {
                Map<String, Object> actor = map("title", role.getPerson().getName(), "label", "actor");
                int source = nodes.indexOf(actor);
                if (source == -1) {
                    nodes.add(actor);
                    source = i++;
                }
                rels.add(map("source", source, "target", target));
            }
        }
        return map("nodes", nodes, "links", rels);
    }

    private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put(key1, value1);
        result.put(key2, value2);
        return result;
    }

    @Transactional(readOnly = true)
    public Map<String, Object>  graph(int limit) {
        Collection<Movie> result = movieRepository.graph(limit);
        return toD3Format(result);
    }

    //    查询电影详情 findByTitle
    @Transactional(readOnly = true)
    public Movie findByTitle(String title) {
        return movieRepository.findByTitle(title);

    }

    //    模糊名查询电影详情 findByTitleLike
    @Transactional(readOnly = true)
    public Collection<Movie> findByTitleLike(String title) {
	    title = ".*"+title+".*";
        return movieRepository.findByTitleLike(title);

    }
    //    查询电影的角色名
    @Transactional(readOnly = true)
    public ArrayList findRoleByTitle(String title) {
        return movieRepository.findRoleByTitle(title);

    }
    //    查询电影的评论 findSummaryByTitle
    @Transactional(readOnly = true)
    public List<String> findSummaryByTitle(String title) {
        return movieRepository.findSummaryByTitle(title);

    }
    //    查询电影的评分 findRatingByTitle
    @Transactional(readOnly = true)
    public List<Long> findRatingByTitle(String title) {
        return movieRepository.findRatingByTitle(title);

    }

}
