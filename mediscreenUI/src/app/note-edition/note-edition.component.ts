import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Note } from '../models/note-model';
import { NoteService } from '../services/note.service';

@Component({
  selector: 'app-note-edition',
  templateUrl: './note-edition.component.html',
  styleUrls: ['./note-edition.component.scss']
})
export class NoteEditionComponent implements OnInit {

  note!: Note;
  public action!:String;

  constructor(private noteService: NoteService,
                   private router:Router) { }

  ngOnInit(): void {
    this.note = window.history.state.customData
    this.action = this.note.id == null ? "Create" : "Update";
  }

  onSubmitForm(form: NgForm):void {
    console.log(this.note);
    if(this.action === "Create"){
      this.noteService.create(this.note).subscribe(() => this.router.navigateByUrl("/person/edit/" + this.note.patientId));
    } else if (this.action === "Update"){
      this.noteService.update(this.note).subscribe(() => this.router.navigateByUrl("/person/edit/" + this.note.patientId));
    }
  }
  
  onCancel(): void {
    }

}
