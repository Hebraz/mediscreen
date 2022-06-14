package com.mediscreen.person.repository;

import com.mediscreen.person.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql({"/schema.sql", "/data.sql"}) //RELOAD database before each test
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;


    @Test
    void injectedComponentsAreNotNull(){
        assertThat(personRepository).isNotNull();
    }

    @Test
    void findAllTest(){
        List<Person> personList = personRepository.findAll();
        assertEquals(23, personList.size());
    }

    @Test
    void saveTest(){
        Person person =  Person.builder().familyName("Paul").givenName("Pierre").address("Paris").phone("77").sex("H").build();
        person =  personRepository.save(person);
        assertNotNull(person.getId());
    }

    @Test
    void findByIdEmpty(){
        Optional<Person> person = personRepository.findById(789);
        assertTrue(person.isEmpty());
    }

    @Test
    void findByIdOk(){
        Optional<Person> person = personRepository.findById(1);
        assertTrue(person.isPresent());
        assertEquals(person.get().getGivenName(), "John");
        assertEquals(person.get().getFamilyName(), "Boyd");
    }

    @Test
    void deleteByKnownId(){
        assertTrue(personRepository.existsById(1));
        personRepository.deleteById(1);
        assertFalse(personRepository.existsById(1));
    }

    @Test
    void deleteByUnknownId(){
        assertFalse(personRepository.existsById(454));
        assertThrows(EmptyResultDataAccessException.class, () -> personRepository.deleteById(454));
    }

    @Test
    void findByFamilyGivenNameNonExistent(){
        Optional<Person>  person = personRepository.findByFamilyNameAndGivenName("Boyd","Johny");
        assertTrue(person.isEmpty());
    }

    @Test
    void findByFamilyGivenNameExistent() {
        Optional<Person> person = personRepository.findByFamilyNameAndGivenName("Boyd", "John");
        assertTrue(person.isPresent());
        assertEquals("841-874-6512", person.get().getPhone());
    }
}