package com.mediscreen.notes.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notes")
@Getter
@Setter
@Builder
public class Note {

    @Id
    private String id;

    private long patientId;
    private String familyName;

    private LocalDateTime date;
    private String description;

    public Note(long patientId, String familyName, LocalDateTime date, String description) {
        this.patientId = patientId;
        this.familyName = familyName;
        this.date = date;
        this.description = description;
    }

    public Note() {
    }

    public Note(String id, long patientId, String familyName, LocalDateTime date, String description) {
        this.id = id;
        this.patientId = patientId;
        this.familyName = familyName;
        this.date = date;
        this.description = description;
    }
}
