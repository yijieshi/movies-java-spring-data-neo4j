package org.neo4j.example.movies.test.movies.hateoas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.neo4j.example.movie.domain.Movie;
import org.neo4j.example.movie.domain.Person;
import org.neo4j.example.movies.test.movies.MoviesClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.http.HttpEntity.EMPTY;

/**
 * @author Frantisek Hartman
 */
public class RestResourceMoviesClient implements MoviesClient {

    private RestTemplate restTemplate;

    public RestResourceMoviesClient() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, getHalMessageConverter());
    }

    private HttpMessageConverter getHalMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        objectMapper.addMixIn(Resource.class, IdResourceSupportMixIn.class);

        objectMapper.registerModule(new Jackson2HalModule());
        MappingJackson2HttpMessageConverter halConverter = new TypeConstrainedMappingJackson2HttpMessageConverter(ResourceSupport.class);
        halConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, HAL_JSON));
        halConverter.setObjectMapper(objectMapper);
        return halConverter;
    }

    @Override
    public Movie findMovie(Long id) {

        try {
            ResponseEntity<Resource<Movie>> response = restTemplate.exchange(
                    "http://localhost:8080/movies/{id}",
                    HttpMethod.GET,
                    EMPTY,
                    new ParameterizedTypeReference<Resource<Movie>>() {},
                    id);

            return response.getBody().getContent();

        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() != HttpStatus.NOT_FOUND) {
                throw ex;
            } else {
                return null;
            }

        }
    }

    @Override
    public Movie findMovie(String title) {
        ResponseEntity<Resource<Movie>> response = restTemplate.exchange(
                "http://localhost:8080/movies/search/findByTitle?title={title}",
                HttpMethod.GET,
                EMPTY,
                new ParameterizedTypeReference<Resource<Movie>>() {},
                title);

        return response.getBody().getContent();
    }

    @Override
    public Movie createMovie(Movie movie) {
        ResponseEntity<Movie> response = restTemplate.postForEntity("http://localhost:8080/movies", movie, Movie.class);
        return response.getBody();
    }

    @Override
    public List<Movie> findMovies(int page, int size) {
        ResponseEntity<PagedResources<Movie>> response = restTemplate.exchange(
                "http://localhost:8080/movies?page={page}&size={size}",
                HttpMethod.GET,
                EMPTY,
                new ParameterizedTypeReference<PagedResources<Movie>>() {},
                page, size);

        return new ArrayList<>(response.getBody().getContent());
    }

    @Override
    public void updateMovie(Movie movie) {
        restTemplate.put("http://localhost:8080/movies/{id}", movie, movie.getId());
    }

    @Override
    public void deleteMovie(Movie movie) {
        restTemplate.delete("http://localhost:8080/movies/{id}", movie.getId());
    }

    @Override
    public Person findPerson(Long id) {
        try {
            ResponseEntity<Resource<Person>> response = restTemplate.exchange(
                    "http://localhost:8080/persons/{id}",
                    HttpMethod.GET,
                    EMPTY,
                    new ParameterizedTypeReference<Resource<Person>>() {},
                    id);

            return response.getBody().getContent();

        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() != HttpStatus.NOT_FOUND) {
                throw ex;
            } else {
                return null;
            }

        }
    }

    @Override
    public Person findPerson(String name) {
        ResponseEntity<Resource<Person>> response = restTemplate.exchange(
                "http://localhost:8080/persons/search/findByName?name={name}",
                HttpMethod.GET,
                EMPTY,
                new ParameterizedTypeReference<Resource<Person>>() {},
                name);

        return response.getBody().getContent();
    }

    @Override
    public List<Person> findPeople(int page, int size) {
        ResponseEntity<PagedResources<Person>> response = restTemplate.exchange(
                "http://localhost:8080/persons?page={page}&size={size}",
                HttpMethod.GET,
                EMPTY,
                new ParameterizedTypeReference<PagedResources<Person>>() {},
                page, size);

        return new ArrayList<>(response.getBody().getContent());
    }

    @Override
    public Person createPerson(Person person) {
        ResponseEntity<Person> response = restTemplate.postForEntity(
                "http://localhost:8080/persons",
                person,
                Person.class);

        return response.getBody();
    }

    @Override
    public void updatePerson(Person person) {
        restTemplate.put("http://localhost:8080/persons/{id}", person, person.getId());
    }

    @Override
    public void deletePerson(Person person) {
        restTemplate.delete("http://localhost:8080/persons/{id}", person.getId());
    }

}
