package org.neo4j.example.movie.ogm;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.example.movie.domain.Movie;
import org.glassfish.jersey.server.ResourceConfig;

import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.drivers.bolt.driver.BoltDriver;
import org.neo4j.ogm.session.SessionFactory;

/**
 * @author Frantisek Hartman
 */
public class MoviesApplication extends ResourceConfig {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MoviesApplication.class);

    public MoviesApplication() {
        // Register resources and providers using package-scanning.
        packages("org.neo4j.examples.movies.ogm");

        // tag::injectable[]
        SessionFactory sf = configureSessionFactoryUsingProperties();
        // Register SessionFactory so it can be injected using @Context annotation
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(sf);
            }
        });
        // end::injectable[]

        register(MoviesService.class);
        register(PeopleService.class);
    }

    // tag::sessionFactory[]
    private SessionFactory configureSessionFactoryUsingProperties() {
        Configuration configuration = new Configuration.Builder(new ClasspathConfigurationSource("ogm.properties"))
                .build();

        return new SessionFactory(configuration, Movie.class.getPackage().getName());
    }
    // end::sessionFactory[]

    private SessionFactory configureSessionFactoryUsingConfigurationBuilder() {
        Configuration configuration = new Configuration.Builder()
                .uri("bolt://localhost:7687")
                .build();

        return new SessionFactory(configuration, Movie.class.getPackage().getName());
    }

    private SessionFactory configureSessionFactoryUsingJava() {
        Driver driver = GraphDatabase.driver("bolt://localhost:7687");
        BoltDriver ogmBoltDriver = new BoltDriver(driver);
        return new SessionFactory(ogmBoltDriver, Movie.class.getPackage().getName());
    }


}
