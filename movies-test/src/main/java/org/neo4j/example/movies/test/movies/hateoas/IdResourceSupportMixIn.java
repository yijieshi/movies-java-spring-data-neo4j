package org.neo4j.example.movies.test.movies.hateoas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author Frantisek Hartman
 */
public abstract class IdResourceSupportMixIn extends ResourceSupport {

    @Override
    @JsonIgnore(false)
    public abstract Link getId();

}
