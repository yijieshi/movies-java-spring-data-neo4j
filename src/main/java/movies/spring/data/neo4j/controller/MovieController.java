package movies.spring.data.neo4j.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.services.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class MovieController {

	private final MovieService movieService;
	
	public MovieController(MovieService movieService) {

		this.movieService = movieService;
	}

	@GetMapping("/graph")
	public Map<String, Object> graph(@RequestParam(value = "limit",required = false) Integer limit) {
		return movieService.graph(limit == null ? 100 : limit);
	}

	//    查询电影详情 findByTitle
	@RequestMapping("/movie/findByTitle")
	public Movie findByTitle(@RequestParam("title")String title) {

		return movieService.findByTitle(title);
	}

	//    模糊名查询电影详情 findByTitleLike
	@RequestMapping("/movie/findByTitleLike")
	public Collection<Movie> findByTitleLike(@RequestParam("title")String title) {

		return movieService.findByTitleLike(title);
	}

	//    查询电影的角色名
	@RequestMapping("/movie/findRoleByTitle")
	public ArrayList findRoleByTitle(@RequestParam("title")String title) {

		return movieService.findRoleByTitle(title);
	}

	//    查询电影的评论 findSummaryByTitle
	@RequestMapping("/movie/findSummaryByTitle")
	public List<String> findSummaryByTitle(@RequestParam("title")String title) {

		return movieService.findSummaryByTitle(title);
	}

	//    查询电影的评分 findRatingByTitle
	@RequestMapping("/movie/findRatingByTitle")
	public List<Long> findRatingByTitle(@RequestParam("title")String title) {

		return movieService.findRatingByTitle(title);
	}
}
