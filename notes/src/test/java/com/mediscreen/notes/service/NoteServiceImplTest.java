package com.mediscreen.notes.service;

import com.mediscreen.notes.exception.NotFoundException;
import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {

    @Mock
    NoteRepository noteRepository;

    private NoteService noteService;

    @BeforeEach
    void init(){
        noteService = new NoteServiceImpl(noteRepository);
    }

    @Test
    void createNote() {
        Note noteToSaved =  Note.builder().familyName("Paul").date(LocalDateTime.now()).description("Nouvelle note").build();
        Note returnedNote = Note.builder().familyName("Paul").date(LocalDateTime.now()).description("Nouvelle note").build();
        when(noteRepository.save(noteToSaved)).thenReturn(returnedNote);

        Note createdNote = noteService.createNote(noteToSaved);

        assertEquals(returnedNote, createdNote);
    }

    @Test
    void updateNoteNonExistent() {
        Note noteToUpdate =  Note.builder().id("AZ456").familyName("Paul").date(LocalDateTime.now()).description("Nouvelle note").build();
        when(noteRepository.findById("AZ456")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> noteService.updateNote(noteToUpdate));
    }

    @Test
    void updateNoteExistent() throws NotFoundException {
        Note noteToUpdate = Note.builder().id("AZ456").build();
        Note noteSaved = Note.builder().id("AZ456").familyName("Paul").date(LocalDateTime.now()).description("Nouvelle note").build();
        when(noteRepository.findById("AZ456")).thenReturn(Optional.of(noteSaved));
        when(noteRepository.save(noteToUpdate)).thenReturn(noteSaved);

        Note retNote = noteService.updateNote(noteToUpdate);

        assertEquals(retNote, noteSaved);
    }

    @Test
    void getNotesByPatientId() {
        List<Note> notes = List.of(
                Note.builder().id("1").familyName("Paul").description("note 1").build(),
                Note.builder().id("2").familyName("Pierre").description("note 2").build(),
                Note.builder().id("3").familyName("Jean").description("note 3").build(),
                Note.builder().id("4").familyName("Louis").description("note 4").build()
        );

        when(noteRepository.findByPatientIdOrderByDateDesc(48L)).thenReturn(notes);

        List<Note> retNotes = noteService.getNotesByPatientId(48L);

        assertEquals(retNotes, notes);
    }
}