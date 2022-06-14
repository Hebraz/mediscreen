package com.mediscreen.person.service;

import com.mediscreen.person.exception.ConflictException;
import com.mediscreen.person.exception.NotFoundException;
import com.mediscreen.person.model.Person;
import com.mediscreen.person.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    PersonRepository personRepository;

    private PersonService personService;

    @BeforeEach
    void init(){
        personService = new PersonServiceImpl(personRepository);
    }

    @Test
    void updatePersonNonExistent() {
        Person p = Person.builder().id(55).build();
        when(personRepository.existsById(55)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> personService.updatePerson(p));
    }

    @Test
    void updatePersonExistent() throws NotFoundException {
        Person p = Person.builder().id(55).build();
        Person personSaved = Person.builder().id(55).familyName("Paul").givenName("Pierre").build();
        when(personRepository.existsById(55)).thenReturn(true);
        when(personRepository.save(p)).thenReturn(personSaved);

        Person retPerson = personService.updatePerson(p);

        assertEquals(retPerson, personSaved);
    }

    @Test
    void createPersonExistent() {
        Person p =  Person.builder().familyName("Paul").givenName("Pierre").build();
        when(personRepository.findByFamilyNameAndGivenName("Paul","Pierre")).thenReturn(Optional.of(p));
        assertThrows(ConflictException.class , () -> personService.createPerson(p));
    }

    @Test
    void createPersonNonExistent() throws ConflictException {
        Person p1 =  Person.builder().familyName("Paul").givenName("Pierre").build();
        Person p2 =  Person.builder().id(24).familyName("Paul").givenName("Pierre").build();
        when(personRepository.findByFamilyNameAndGivenName("Paul","Pierre")).thenReturn(Optional.empty());
        when(personRepository.save(p1)).thenReturn(p2);

        Person createdPerson = personService.createPerson(p1);

        assertEquals(24, createdPerson.getId());

    }

    @Test
    void deletePersonByIdNonExistent() {
        final int id = 55;
        Person p = Person.builder().id(id).build();
        when(personRepository.existsById(id)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> personService.deletePersonById(id));

        verify(personRepository, never()).deleteById(id);
    }

    @Test
    void deletePersonByIdExistent() throws NotFoundException {
        final int id = 55;
        Person p = Person.builder().id(id).build();
        Person personSaved = Person.builder().id(id).familyName("Paul").givenName("Pierre").build();
        when(personRepository.existsById(id)).thenReturn(true);


        personService.deletePersonById(id);

        verify(personRepository, times(1)).deleteById(id);
    }

    @Test
    void getPersons() {
        List<Person> personList = new ArrayList<>();
        when(personRepository.findAll()).thenReturn(personList);

        List<Person> personListReturnByService = personService.getPersons();

        verify(personRepository, times(1)).findAll();
        assertEquals(personListReturnByService, personList);
    }
}