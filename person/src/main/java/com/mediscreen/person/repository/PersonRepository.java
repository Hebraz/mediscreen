package com.mediscreen.person.repository;

import com.mediscreen.person.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    /**
     * Find a person according to its family name and its given name
     * @param familyName
     * @param givenName
     * @return person instance if exist in DB
     */
    Optional<Person> findByFamilyNameAndGivenName(String familyName, String givenName);
}
