package movies.spring.data.neo4j.domain;


import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RelationshipEntity(type = "REVIEWED")
public class Review {
    @Id
    @GeneratedValue
    private Long id;
    private Long rating;
    private String summary;

    @StartNode
    private Person person;

    @EndNode
    private Movie movie;

    public Review() {
    }

    public Review(Movie movie, Person actor) {
        this.movie = movie;
        this.person = actor;
    }

}
