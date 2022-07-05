import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Person } from '../models/person.model';
import { PersonService } from '../services/person.service';

@Component({
  selector: 'app-person',
  templateUrl: './person.component.html',
  styleUrls: ['./person.component.scss']
})
export class PersonComponent implements OnInit {

  @Input() person!: Person;

  public deleted!: boolean;
  constructor(private personService: PersonService, 
                private route: ActivatedRoute,
                private router : Router){ }

  ngOnInit(): void {
    const id = +this.route.snapshot.params['id'];
    this.deleted=false;
  }

  onDelete(id:number): void{
    
    this.personService.delete(id).subscribe(() => {
      this.deleted=true;
      this.router.navigateByUrl('/persons');
    }   );
  
   }

  onView(id:number): void{
    this.router.navigateByUrl('/person/' + id);
  }
}
