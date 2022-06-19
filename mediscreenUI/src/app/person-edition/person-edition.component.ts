import { Component, Input, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Person } from '../models/person.model';
import { PersonComponent } from '../person/person.component';
import { PersonService } from '../services/person.service';

@Component({
  selector: 'app-person-edition',
  templateUrl: './person-edition.component.html',
  styleUrls: ['./person-edition.component.scss']
})
export class PersonEditionComponent implements OnInit {
  
  person!: Person;
  public action!:String;

  constructor(private personService: PersonService, 
    private router: Router) { }

  ngOnInit(): void {

      this.person = window.history.state.customData
      this.action = this.person.id === 0 ? "Create" : "Update";
  }
  
  onSubmitForm(form: NgForm):void {
    if(this.action === "Create"){
      this.personService.create(form.value).subscribe(() => this.router.navigateByUrl("/persons"));
    } else if (this.action === "Update"){
      this.personService.update(form.value).subscribe(() => this.router.navigateByUrl("/persons"));
    }
  }
  
  onCancel(): void {
    this.router.navigate(['/persons']);
  }
}



