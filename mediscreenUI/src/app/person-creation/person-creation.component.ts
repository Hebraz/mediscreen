import { Component, OnInit } from '@angular/core';
import { Person } from '../models/person.model';

@Component({
  selector: 'app-person-creation',
  templateUrl: './person-creation.component.html',
  styleUrls: ['./person-creation.component.scss']
})
export class PersonCreationComponent implements OnInit {

  public person!:Person;

  constructor() { }

  ngOnInit(): void {
    this.person = new Person(0,"","", new Date(),"","","");
  }

}
