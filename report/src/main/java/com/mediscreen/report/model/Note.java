package com.mediscreen.report.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
public class Note {
    private String id;
    private long patientId;
    private String familyName;
    private LocalDateTime date;
    private String description;
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
