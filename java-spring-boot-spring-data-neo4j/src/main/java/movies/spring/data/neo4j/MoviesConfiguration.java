package movies.spring.data.neo4j;

import org.neo4j.example.movie.domain.Movie;
import org.neo4j.example.movie.domain.Person;
import org.neo4j.example.movie.domain.Role;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.neo4j.web.support.OpenSessionInViewInterceptor;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.servlet.handler.MappedInterceptor;

/**
 * @author Frantisek Hartman
 */
@Configuration
public class MoviesConfiguration {

    @Bean
    public OpenSessionInViewInterceptor openSessionInViewInterceptor() {
        return new OpenSessionInViewInterceptor();
    }

    /**
     * Using MappedInterceptor, configuration through org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
     * doesn't work for Spring Data REST controllers
     */
    @Bean
    public MappedInterceptor myMappedInterceptor() {
        return new MappedInterceptor(new String[]{"/**"}, openSessionInViewInterceptor());
    }

    @Configuration
    public static class RepositoryConfig extends RepositoryRestMvcConfiguration {

        public RepositoryConfig(ApplicationContext context, ObjectFactory<ConversionService> conversionService) {
            super(context, conversionService);
        }

        @Override
        public RepositoryRestConfiguration repositoryRestConfiguration() {
            return super.repositoryRestConfiguration().exposeIdsFor(Movie.class, Person.class, Role.class);
        }

    }

}
