package com.mediscreen.notes;

import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.repository.NoteRepositoory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Calendar;

@SpringBootApplication
public class NotesApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(NotesApplication.class, args);
	}

	@Autowired
	NoteRepositoory repository;

	@Override
	public void run(String... args) throws Exception {
		repository.save(new Note("2","42", LocalDateTime.now(),"sqfsfqezz"));
	}
}
