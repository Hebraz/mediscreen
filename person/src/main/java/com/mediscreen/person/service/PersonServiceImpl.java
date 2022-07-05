package com.mediscreen.person.service;

import com.mediscreen.person.model.Person;
import com.mediscreen.person.exception.*;
import com.mediscreen.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Updates person into database. Cannot be use to create a person (null id)
     *
     * @param person person instance to update
     * @return the saved person.
     * @throws NotFoundException when trying to update a non-existing person
     */
    @Override
    public Person updatePerson(Person person) throws NotFoundException {
        if (personRepository.existsById(person.getId())) {
            return personRepository.save(person);
        } else {
            throw new NotFoundException("Cannot update non existing user : " + person.getFamilyName() + " " + person.getGivenName());
        }
    }

    /**
     * Creates person into database. Cannot be used to update a person (non-null id)
     *
     * @param person person instance to create
     * @return the saved person.
     * @throws ConflictException when trying to create an existing person
     */
    @Override
    public Person createPerson(Person person) throws ConflictException {
        Optional<Person> personInDb = personRepository.findByFamilyNameAndGivenName(person.getFamilyName(),person.getGivenName());
        if(personInDb.isEmpty()) {
            return personRepository.save(person);
        } else {
            throw new ConflictException("Cannot create existing user : " +  person.getFamilyName() + " " + person.getGivenName());
        }
    }

    /**
     * Deletes person from database.
     *
     * @param id of the person to delete
     * @throws NotFoundException when trying to delete a non existent person
     */
    @Override
    public void deletePersonById(int id) throws NotFoundException {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
        } else {
            throw new NotFoundException("Cannot delete non existing user id : " + id);
        }
    }

    /**
     * Get a person
     * @param id of the person
     * @return optional of Person
     */
    @Override
    public Optional<Person> getPerson(int id){
        return personRepository.findById(id);
    }

    /**
     * Gets person list from database.
     *
     * @return a list of person instances.
     */
    @Override
    public List<Person> getPersons() {
        return personRepository.findAll();
    }
}
