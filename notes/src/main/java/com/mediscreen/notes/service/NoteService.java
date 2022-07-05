package com.mediscreen.notes.service;

import com.mediscreen.notes.exception.NotFoundException;
import com.mediscreen.notes.model.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    /**
     * Creates a Practitioner's note/recommendation for a given patient
     * @param note a Note instance
     * @return the created note
     */
    Note createNote(Note note);

    /**
     * Saves a Practitioner's note/recommendation for a given patient
     * @param note a Note instance
     * @return the updated note
     */
    Note updateNote(Note note) throws NotFoundException;

    /**
     * Get list of Practitioner's notes/recommendations for a given patient
     * @param patientId id of the patien
     * @return a list of notes
     */
    List<Note> getNotesByPatientId(long patientId);
}
