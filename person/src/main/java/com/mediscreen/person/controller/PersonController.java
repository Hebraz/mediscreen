package com.mediscreen.person.controller;

import com.mediscreen.person.model.Person;
import com.mediscreen.person.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.mediscreen.person.exception.*;
import java.util.List;

@Controller
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Add a new person.
     *
     * @param person An object Person
     *
     * @return  HTTP response with :
     *              Body : the created Person object.
     *              Http status code : "201-Created" if person have been created.
     *
     * @throws ConflictException if a person with the same given name and family name
     *                                      already exists in datasource
     */
    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) throws ConflictException {
        return null;
    }

    /**
     * Update an existing person.
     *
     * @param person An object Person
     *
     * @return  HTTP response with :
     *              Body : the updated Person object.
     *              Http status code :  "200-Ok" if person have been updated.
     *
     * @throws NotFoundException if no person with the same given name and family name
     *                                exists in datasource
     */
    @PutMapping("/person")
    public  ResponseEntity<Person>  updatePerson(@RequestBody Person person) throws NotFoundException {
        return null;
    }

    /**
     * Delete a person.
     *
     * @param id - id of the person to delete
     * @return  HTTP response with :
     *            - empty Body
     *            - Http status code set to "204-No Content" if person have been deleted.
     *
     * @throws NotFoundException if no person with the given id exists in datasource
     */
    @DeleteMapping("/person/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable("id") final Integer id) throws NotFoundException {
      return null;
    }

    /**
     * Get person list.
     *
     * @return  HTTP response with :
     *            - empty Body
     *            - Http status code set to "200-Ok"
     *
     */
    @GetMapping("/persons")
    public ResponseEntity<List<Person>> getPersons() {
        return null;
    }
}
