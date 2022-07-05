package com.mediscreen.notes;

import com.mediscreen.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotesApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(NotesApplication.class, args);
	}

	@Autowired
	NoteRepository repository;

	@Override
	public void run(String... args) throws Exception {

	}
}
