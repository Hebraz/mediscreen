import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Person } from '../models/person.model';
import { PersonService } from '../services/person.service';

@Component({
  selector: 'app-person-update',
  templateUrl: './person-update.component.html',
  styleUrls: ['./person-update.component.scss']
})
export class PersonUpdateComponent implements OnInit {

  public person$! : Observable<Person>;

  constructor(private personService: PersonService, 
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    this.person$ = this.personService.getPersonById(id);
  }

}
