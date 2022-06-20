package com.mediscreen.notes.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Document(collection = "notes")
@Getter
@Setter
public class Note {

    @Id
    private String id;

    private String patId;
    private LocalDateTime date;
    private String description;

    public Note(String id, String patId, LocalDateTime date, String description) {
        this.patId = patId;
        this.date = date;
        this.description = description;
    }

    public Note() {
    }
}
