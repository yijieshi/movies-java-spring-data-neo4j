package org.neo4j.example.movie.ogm;

//import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.neo4j.example.movie.domain.Movie;
import org.glassfish.jersey.server.ResourceConfig;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;

/**
 * @author Frantisek Hartman
 */
public class MoviesApplication extends ResourceConfig {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MoviesApplication.class);

    public MoviesApplication() {
        // Register resources and providers using package-scanning.
        packages("org.neo4j.examples.movies.ogm");

        Configuration configuration = new Configuration.Builder()
                .uri("bolt://localhost:7687")
                .build();
        SessionFactory sf = new SessionFactory(configuration, Movie.class.getPackage().getName());
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(sf);
            }
        });

        register(MoviesService.class);
        register(PeopleService.class);

//        register();

        // Enable Tracing support.
//        property(ServerProperties.TRACING, "ALL");
    }
}
