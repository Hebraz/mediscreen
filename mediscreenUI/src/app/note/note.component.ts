import { Component, Input, OnInit } from '@angular/core';
import { Note } from '../models/note-model';
import { NoteService } from '../services/note.service';

@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  styleUrls: ['./note.component.scss']
})
export class NoteComponent implements OnInit {

  @Input() note!:Note;
  editMode!: boolean;
  unsavedNote!: string;

  constructor(private noteService:NoteService) { 

  }

  ngOnInit(): void {
    this.editMode = false;
    this.unsavedNote = this.note.description;
  }

  onEdit(): void{
    this.editMode = true;
  }

  onSave(): void{
    this.note.date = new Date();
    this.note.description = this.unsavedNote;
    this.noteService.update(this.note).subscribe();
    this.editMode = false;
  }

  onCancel(): void{
    this.unsavedNote = this.note.description;
    this.editMode = false;  
  }

}
