import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Person } from '../models/person.model';
import { PersonService } from '../services/person.service';

@Component({
  selector: 'app-person',
  templateUrl: './person.component.html',
  styleUrls: ['./person.component.scss']
})
export class PersonComponent implements OnInit {

  @Input() person!: Person;
  
  public areDetailsShown!: boolean;
  public deleted!: boolean;
  constructor(private personService: PersonService, private router : Router){ }

  ngOnInit(): void {
    this.areDetailsShown = false;
    this.deleted=false;
  }

  onToggleDetails(): void{
    this.areDetailsShown = !this.areDetailsShown;
  }

  onDelete(): void{
    this.personService.delete(this.person.id).subscribe(() => this.deleted = true);
  }

  onEdit(): void{
    this.router.navigate(['/person'], { state: {customData: this.person}});
  }
}
