package com.mediscreen.person.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mediscreen.person.Json;
import com.mediscreen.person.model.Person;
import com.mediscreen.person.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.is;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql({"/schema.sql", "/data.sql"}) //RELOAD database before each test
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonRepository personRepository;



    @Test
    void createPersonExistent() throws Exception {
        Person p = Person.builder().familyName("Zemicks").givenName("Sophia").build();

        mockMvc.perform(post("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json.fromObject(p)))
                .andExpect(status().isConflict());
    }

    @Test
    void createPersonNonExistent() throws Exception {
        Person p = Person.builder().familyName("Zemicks").givenName("Sophie").build();

       assertTrue(personRepository.findByFamilyNameAndGivenName("Zemicks", "Sophie").isEmpty());

        mockMvc.perform(post("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json.fromObject(p)))
                .andExpect(status().isCreated());

        Optional<Person> createdPerson = personRepository.findByFamilyNameAndGivenName("Zemicks", "Sophie");

        assertTrue(createdPerson.isPresent());
        assertEquals(24, createdPerson.get().getId());
    }

    @Test
    void updatePerson() throws Exception {

        Person p = Person.builder().id(3).familyName("Boyd").givenName("Tenley").phone("123456789").build();

        mockMvc.perform(put("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json.fromObject(p)))
                .andExpect(status().isOk());

        Optional<Person> updatedPerson = personRepository.findById(3);
        assertTrue(updatedPerson.isPresent());
        assertEquals("123456789", updatedPerson.get().getPhone());

    }

    @Test
    void deletePerson() throws Exception {
        assertTrue(personRepository.findById(17).isPresent());
        mockMvc.perform(delete("/person/17"))
                .andExpect(status().isNoContent());
        assertTrue(personRepository.findById(17).isEmpty());

    }

    @Test
    void getPersons() throws Exception {

        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(23)));
    }

    @Test
    void getPersonFound() throws Exception {

        mockMvc.perform(get("/person/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.familyName", is("Boyd")))
                .andExpect(jsonPath("$.givenName", is("John")));
    }

    @Test
    void getPersonNotFound() throws Exception {

        mockMvc.perform(get("/person/789"))
                .andExpect(status().isNotFound());
    }
}