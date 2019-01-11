package movies.spring.data.neo4j.controller;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.List;

import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.domain.Person;
import movies.spring.data.neo4j.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mark Angrish
 * @author Michael J. Simons
 */
@RestController
@RequestMapping("/")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {

        this.personService = personService;
    }

    //    查询明星详情
    @RequestMapping("/person/findByName")
    public Person findByName(@RequestParam("name")String name) {

        return personService.findByName(name);
    }

    //    查询明星在电影的角色名
    @RequestMapping("/person/findMovieByName")
    public ArrayList findRoleByNameAndTitle(@RequestParam("name") String name, @RequestParam("title") String title) {

        return personService.findRoleByNameAndTitle(name,title);
    }

}
