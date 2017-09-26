package org.neo4j.example.movie.ogm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.neo4j.ogm.cypher.query.Pagination;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.transaction.Transaction;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Frantisek Hartman
 */
public abstract class BaseEntityService<T> {

    private final Class<T> clazz;

    @Context
    protected SessionFactory sessionFactory;

    protected BaseEntityService(Class<T> clazz) {
        this.clazz = clazz;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public T findById(@PathParam("id") Long id) {

        Session session = sessionFactory.openSession();
        try (Transaction tx = session.beginTransaction()) {
            T entity = session.load(clazz, id, 0);
            tx.commit();
            return entity;
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public T createEntity(T entity) {
        Session session = sessionFactory.openSession();

        try (Transaction tx = session.beginTransaction()) {
            session.save(entity);

            tx.commit();
            return entity;
        }
    }

    protected Map<String, Object> map(Object... keyValues) {
        if (keyValues.length % 2 != 0) {
            throw new IllegalArgumentException("Expected array of even length");
        }
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < keyValues.length; i += 2) {
            if (!(keyValues[i] instanceof String)) {
                throw new IllegalArgumentException("Every even element must be a String");
            }
            params.put((String) keyValues[i], keyValues[i + 1]);
        }

        return params;
    }

    protected static <T> T single(Iterable<T> movies) {
        Iterator<T> it = movies.iterator();
        T entity = it.next();
        if (it.hasNext()) {
            throw new IllegalStateException("Expected only 1 movie");
        }
        return entity;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<T> findEntities(@QueryParam("page") @DefaultValue("0") int page,
                                @QueryParam("size") @DefaultValue("100") int size) {

        Session session = sessionFactory.openSession();
        try (Transaction tx = session.beginTransaction()) {
            List<T> movies = newArrayList(session.loadAll(clazz, new Pagination(page, size), 0));

            tx.commit();
            return movies;
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateEntity(T entity) {
        Session session = sessionFactory.openSession();

        try (Transaction tx = session.beginTransaction()) {
            session.save(entity);

            tx.commit();
        }
    }

    @DELETE
    @Path("/{id}")
    public void deleteEntity(@PathParam("id") Long id) {
        Session session = sessionFactory.openSession();

        try (Transaction tx = session.beginTransaction()) {
            T movie = session.load(clazz, id, 0);
            session.delete(movie);

            tx.commit();
        }
    }
}
