import { Component, OnInit } from '@angular/core';
import { PersonService } from '../services/person.service';
import { Observable } from 'rxjs';
import { Person } from '../models/person.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.scss']
})
export class PersonListComponent implements OnInit {

  persons$!: Observable<Person[]>;

  constructor(private personService: PersonService,
    private router: Router) { }

  ngOnInit(): void {
    this.persons$ = this.personService.getAllPersons();
  }

  onCreate(){
    this.router.navigateByUrl('/person/create');
  }
}
