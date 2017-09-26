package org.neo4j.example.movie.ogm;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.neo4j.example.movie.domain.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Frantisek Hartman
 */
@Path("/movies")
@Singleton
public class MoviesService extends BaseEntityService<Movie> {

    private static final Logger logger = LoggerFactory.getLogger(MoviesService.class);

    public MoviesService() {
        super(Movie.class);
        logger.info("MovieService created");
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Movie findByTitle(@QueryParam("title") String title) {

        Session session = sessionFactory.openSession();
        try (Transaction tx = session.beginTransaction()) {
            Movie movie = single(session.query(Movie.class, "MATCH (m:Movie) WHERE m.title = {title} RETURN m",
                    map("title", title)));

            tx.commit();
            return movie;
        }
    }

}
