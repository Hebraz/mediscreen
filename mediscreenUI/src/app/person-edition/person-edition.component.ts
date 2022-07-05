import { Component, Input, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Person } from '../models/person.model';
import { PersonComponent } from '../person/person.component';
import { PersonService } from '../services/person.service';

@Component({
  selector: 'app-person-edition',
  templateUrl: './person-edition.component.html',
  styleUrls: ['./person-edition.component.scss']
})
export class PersonEditionComponent implements OnInit {
  
  @Input() action!:string;
  @Input() person!:Person;

  constructor(private personService: PersonService, 
    private router: Router) { }

  ngOnInit(): void {
  }
  
  onSubmitForm(form: NgForm):void {
    if(this.action === "create"){
      this.personService.create(form.value).subscribe(() => this.router.navigateByUrl("/persons"));
    } else if (this.action === "edit"){
      this.personService.update(form.value).subscribe(() => this.router.navigateByUrl("/person/" + this.person.id));
    }
  }
  
  onCancel(id:number): void {
    if(this.action === "create"){
      this.router.navigateByUrl("/persons");
    } else {
      this.router.navigateByUrl('/person/' + id);
    }
    
  }
}



