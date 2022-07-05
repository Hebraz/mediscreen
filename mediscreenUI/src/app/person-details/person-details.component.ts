import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Note } from '../models/note-model';
import { Person } from '../models/person.model';
import { NoteService } from '../services/note.service';
import { PersonService } from '../services/person.service';

@Component({
  selector: 'app-person-details',
  templateUrl: './person-details.component.html',
  styleUrls: ['./person-details.component.scss']
})
export class PersonDetailsComponent implements OnInit {

  person$ !:Observable<Person>;
  note$!: Observable<Note[]>;
  private person!:Person;
  private personId!:number;
  public description!:string;

  constructor(private noteService: NoteService,
              private personService: PersonService,
              private route: ActivatedRoute,
              private router: Router,
              private changeDetectorRef:ChangeDetectorRef ) { }

  ngOnInit(): void {
    this.personId = +this.route.snapshot.params['id'];
    this.person$ = this.personService.getPersonById(this.personId);
    this.note$ = this.noteService.getPatientNotes(this.personId);
    
    this.person$.subscribe(person => this.person = person);
  }
  onCreateNote(): void{
    var note = new Note('', this.person.id, this.person.familyName, new Date() , this.description );
    this.noteService.create(note).subscribe(() => this.note$ = this.noteService.getPatientNotes(this.personId));
  }

  onDelete(id:number): void{
    
    this.personService.delete(id).subscribe(() => {
       this.router.navigate(['/persons']);
    }   );
  
   }

  onEdit(id:number): void{
    this.router.navigateByUrl('/person/edit/' + id);
  }
}
