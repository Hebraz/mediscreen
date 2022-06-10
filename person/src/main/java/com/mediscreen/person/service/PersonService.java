package com.mediscreen.person.service;

import com.mediscreen.person.exception.*;
import com.mediscreen.person.model.Person;

public interface PersonService {
    /**
     * Updates person into database. Cannot be use to create a person (null id)
     * @param person person instance to update
     * @return the saved person.
     * @throws NotFoundException when trying to update a non-existing person
     */
    Person updatePerson(Person person);

    /**
     * Creates person into database. Cannot be used to update a person (non-null id)
     * @param person person instance to create
     * @return the saved person.
     * @throws ConflictException when trying to create an existing person
     */
    Person createPerson(Person person);

    /**
     * Deletes person from database.
     * @param id of the person to delete
     * @throws NotFoundException when trying to delete a non existent person
     */
    void deletePersonById(int id);

    /**
     * Gets person list from database.
     *
     * @return a list of person instances.
     */
    public Person getPersons();
}
