package org.neo4j.example.movie.sdn.service;

import java.util.List;

import org.neo4j.example.movie.sdn.domain.Person;
import org.neo4j.example.movie.sdn.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.collect.Lists.newArrayList;
import static org.springframework.data.domain.PageRequest.of;

/**
 * @author Frantisek Hartman
 */
@Service
@Transactional
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person findById(Long id) {
        return personRepository.findById(id, 0).orElse(null);
    }

    public Person findByName(String name) {
        return personRepository.findByName(name, 0);
    }

    public List<Person> findAll(int page, int size) {
        return newArrayList(personRepository.findAll(of(page, size), 0));
    }

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public Person updatePerson(Person person) {
        return personRepository.save(person);
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}
