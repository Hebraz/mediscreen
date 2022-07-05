package com.mediscreen.notes.service;

import com.mediscreen.notes.exception.NotFoundException;
import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Note service
 */
@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    /**
     * Creates a Practitioner's note/recommendation for a given patient
     * @param note a Note instance
     * @return the created note
     */
    public Note createNote(Note note){
        note.setId(null);
        return noteRepository.save(note);
    }

    /**
     * Saves a Practitioner's note/recommendation for a given patient
     * @param note a Note instance
     * @return the updated note
     */
    public Note updateNote(Note note) throws NotFoundException {
        Optional<Note> noteOptional = noteRepository.findById(note.getId());
        if(noteOptional.isPresent()){
            return noteRepository.save(note);
        } else {
            throw new NotFoundException("Cannot update non existing note : " + note.getId());
        }
    }

    /**
     * Get list of Practitioner's notes/recommendations for a given patient
     * @param patientId id of the patien
     * @return a list of notes
     */
    public List<Note> getNotesByPatientId(long patientId){
        return  noteRepository.findByPatientIdOrderByDateDesc(patientId);
    }
}
