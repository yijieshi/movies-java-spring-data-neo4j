package movies.spring.data.neo4j.services;

import java.util.*;

import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.domain.Person;
import movies.spring.data.neo4j.domain.Role;
import movies.spring.data.neo4j.repositories.MovieRepository;
import movies.spring.data.neo4j.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {
    private final static Logger LOG = LoggerFactory.getLogger(PersonService.class);

    private final PersonRepository personRepository;
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }



    //    查询明星详情
    @Transactional(readOnly = true)
    public Person findByName(String name) {
        return personRepository.findByName(name);

    }
    //    查询明星在电影的角色名
    @Transactional(readOnly = true)
    public ArrayList findRoleByNameAndTitle(String name, String title) {
        return personRepository.findRoleByNameAndTitle(name,title);

    }

}
