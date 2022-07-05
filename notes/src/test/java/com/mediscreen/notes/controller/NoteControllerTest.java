package com.mediscreen.notes.controller;

import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.repository.NoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void  initTest(){
        List<Note> notes = List.of(
                Note.builder().id("62b1b9fe6bbcc85af6887451").patientId(1).familyName("Paul").date(LocalDateTime.now().minusDays(10)).description("Note 1").build(),
                Note.builder().id("62b1b9fe6bbcc85af6887452").patientId(1).familyName("Paul").date(LocalDateTime.now().minusDays(11)).description("Note 2").build(),
                Note.builder().id("62b1b9fe6bbcc85af6887453").patientId(1).familyName("Paul").date(LocalDateTime.now().minusDays(10)).description("Note 3").build(),
                Note.builder().id("62b1b9fe6bbcc85af6887454").patientId(2).familyName("Pierre").date(LocalDateTime.now().minusDays(10)).description("Note 4").build(),
                Note.builder().id("62b1b9fe6bbcc85af6887455").patientId(2).familyName("Pierre").date(LocalDateTime.now().minusDays(13)).description("Note 5").build(),
                Note.builder().id("62b1b9fe6bbcc85af6887456").patientId(2).familyName("Pierre").date(LocalDateTime.now().minusDays(12)).description("Note 6").build(),
                Note.builder().id("62b1b9fe6bbcc85af6887457").patientId(2).familyName("Pierre").date(LocalDateTime.now().minusDays(9)).description("Note 7").build()


        );


        for (Note note : notes) {
            mongoTemplate.insert(note);
        }
    }

    @AfterEach
    void finalyzeTest(){
        mongoTemplate.getDb().drop();
    }


    @Test
    void injectedComponentsAreNotNull(){
        assertThat(noteRepository).isNotNull();
    }

    @Test
    void testConfig(){
        Optional<Note> note = noteRepository.findById("62b1b9fe6bbcc85af6887451");
        assertTrue(note.isPresent());
    }

    @Test
    void addPatientNote() throws Exception {
        List<Note> notes = noteRepository.findAll();
        assertEquals(7, notes.size());

        mockMvc.perform(post("/patHistory/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"patientId\": 3," +
                                "\"familyName\":\"Jean\","+
                                "\"date\": \"2022-11-21T18:54:30\","+
                                "\"description\":\"Nouvelle note pour M.Jean\" " +
                                "}"))
                .andExpect(status().isCreated());

        notes = noteRepository.findAll();
        assertEquals(8, notes.size());
        assertEquals("Nouvelle note pour M.Jean", notes.get(7).getDescription());

    }

    @Test
    void updatePatientNoteOk() throws Exception {
        mockMvc.perform(put("/patHistory/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\":\"62b1b9fe6bbcc85af6887452\","+
                                "\"patientId\": 1," +
                                "\"familyName\":\"Pierre\","+
                                "\"date\": \"2022-11-21T18:54:30\","+
                                "\"description\":\"Mise à jour de la note 2\" " +
                                "}"))
                .andExpect(status().isOk());

        List<Note> notes = noteRepository.findAll();
        assertEquals(7, notes.size());
        assertEquals("Mise à jour de la note 2", notes.get(1).getDescription());

    }


    @Test
    void updatePatientNoteNotFound() throws Exception {
        mockMvc.perform(put("/patHistory/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\":\"Unknown id\","+
                                "\"patientId\": 1," +
                                "\"familyName\":\"Pierre\","+
                                "\"date\": \"2022-11-21T18:54:30\","+
                                "\"description\":\"Mise à jour de la note 2\" " +
                                "}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getNotes() throws Exception {
        mockMvc.perform(get("/patHistory/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }
}