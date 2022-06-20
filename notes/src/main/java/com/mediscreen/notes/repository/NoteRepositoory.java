package com.mediscreen.notes.repository;

import com.mediscreen.notes.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepositoory extends MongoRepository<Note, String > {
}
