package org.neo4j.example.movie.ogm;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.neo4j.example.movie.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.transaction.Transaction;

/**
 * @author Frantisek Hartman
 */
@Path("/people")
@Singleton
public class PeopleService extends BaseEntityService<Person> {

    private static final Logger logger = LoggerFactory.getLogger(PeopleService.class);

    @Context
    private SessionFactory sessionFactory;

    public PeopleService() {
        super(Person.class);
    }


    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Person findByTitle(@QueryParam("name") String name) {

        Session session = sessionFactory.openSession();
        try (Transaction tx = session.beginTransaction()) {
            Person movie = single(session.query(Person.class, "MATCH (p:Person) WHERE p.name = {name} RETURN p",
                    map("name", name)));

            tx.commit();
            return movie;
        }
    }
}
