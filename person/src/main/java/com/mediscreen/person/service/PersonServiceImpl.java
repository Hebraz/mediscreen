package com.mediscreen.person.service;

import com.mediscreen.person.model.Person;
import com.mediscreen.person.exception.*;

public class PersonServiceImpl implements PersonService {
    /**
     * Updates person into database. Cannot be use to create a person (null id)
     *
     * @param person person instance to update
     * @return the saved person.
     * @throws NotFoundException when trying to update a non-existing person
     */
    @Override
    public Person updatePerson(Person person) {
        return null;
    }

    /**
     * Creates person into database. Cannot be used to update a person (non-null id)
     *
     * @param person person instance to create
     * @return the saved person.
     * @throws ConflictException when trying to create an existing person
     */
    @Override
    public Person createPerson(Person person) {
        return null;
    }

    /**
     * Deletes person from database.
     *
     * @param id of the person to delete
     * @throws NotFoundException when trying to delete a non existent person
     */
    @Override
    public void deletePersonById(int id) {

    }

    /**
     * Gets person list from database.
     *
     * @return a list of person instances.
     */
    @Override
    public Person getPersons() {
        return null;
    }
}
