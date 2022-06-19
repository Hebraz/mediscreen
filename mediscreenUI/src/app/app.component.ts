import { Component, OnInit } from '@angular/core';
import { Person } from './models/person.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent  implements OnInit{

  ngOnInit(): void {
  }
  
  title = 'mediscreenUI';

  
}
