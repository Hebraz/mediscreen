package com.mediscreen.notes.repository;

import com.mediscreen.notes.model.Note;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class NoteRepositoryTest {
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
    void findByPatientIdOrderByDateDesc(){
        List<Note> notes = noteRepository.findByPatientIdOrderByDateDesc(2L);
        assertEquals(4, notes.size());
        //check that notes are ordered
        assertEquals("Note 7", notes.get(0).getDescription());
        assertEquals("Note 4", notes.get(1).getDescription());
        assertEquals("Note 6", notes.get(2).getDescription());
        assertEquals("Note 5", notes.get(3).getDescription());
    }

    @Test
    void save(){
        List<Note> notes = noteRepository.findAll();
        assertEquals(7, notes.size());

        Note noteToSave = Note.builder().familyName("Jean").date(LocalDateTime.now()).description("Nouvelle note").build();
        Note savedNote = noteRepository.save(noteToSave);

        notes = noteRepository.findAll();
        assertEquals(8, notes.size());

        assertEquals("Nouvelle note", notes.get(7).getDescription());
        assertNotNull(savedNote.getId());
    }

}