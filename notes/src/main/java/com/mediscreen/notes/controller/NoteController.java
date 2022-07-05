package com.mediscreen.notes.controller;

import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.service.NoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.mediscreen.notes.exception.*;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(maxAge = 360)
public class NoteController {

    private final NoteServiceImpl noteService;

    @Autowired
    public NoteController(NoteServiceImpl noteService) {
        this.noteService = noteService;
    }

    /**
     * Adds a note to patient history
     *
     * @param note An object Note
     *
     * @return  HTTP response with :
     *              Body : the created Note object.
     *              Http status code : "201-Created" if note have been created.
     *
     */
    @PostMapping(path = "/patHistory")
    public ResponseEntity<String> addPatientNote(@RequestBody Note note) {

        Note createdNote = noteService.createNote(note);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdNote.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * Update an existing note.
     *
     * @param note An object Note
     *
     * @return  HTTP response with :
     *              Body : the updated Note object.
     *              Http status code :  "200-Ok" if note have been updated.
     *
     * @throws NotFoundException if no note with the same id exist
     */
    @PutMapping(path = "/patHistory")
    public ResponseEntity<String> updatePatientNote(@RequestBody Note note) throws NotFoundException {
        noteService.updateNote(note);
        return ResponseEntity.ok().build();
    }

    /**
     * Get list of notes fot a given patient.
     * @param id of the patient
     * @return  HTTP response with :
     *            - empty Body
     *            - Http status code set to "200-Ok"
     *
     */
    @GetMapping("/patHistory/{id}")
    public ResponseEntity<List<Note>> getNotes(@PathVariable("id") final Long id) {
        List<Note> notes = noteService.getNotesByPatientId(id);
        if(notes.isEmpty()){
            return ResponseEntity.ok().build();
        } else {
            return  ResponseEntity.ok(notes);
        }
    }
}
