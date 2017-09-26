package org.neo4j.example.movie.sdn.controller;

import java.util.List;

import org.neo4j.example.movie.sdn.domain.Person;
import org.neo4j.example.movie.sdn.service.PersonService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Frantisek Hartman
 */
@RestController
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/people/{id}")
    public Person findById(@PathVariable("id") Long id) {
        return personService.findById(id);
    }

    @GetMapping("/people/search")
    public Person findByName(@RequestParam("name") String name) {
        return personService.findByName(name);
    }

    @GetMapping("/people")
    public List<Person> findById(@RequestParam("page") int page,
                                 @RequestParam("size") int size) {
        return personService.findAll(page, size);
    }

    @PostMapping("/people")
    public Person createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @PutMapping("/people")
    public Person updatePerson(@RequestBody Person person) {
        return personService.updatePerson(person);
    }

    @DeleteMapping("/people/{id}")
    public void deletePerson(@PathVariable("id") Long id) {
        personService.deletePerson(id);
    }


}
