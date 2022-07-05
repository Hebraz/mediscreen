import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Person } from "../models/person.model";
import { catchError, Observable } from "rxjs";
import { Router } from "@angular/router";

@Injectable({
    providedIn: 'root'
})



export class PersonService{
    //un service n'a pas de méthode  ngOnInit(), 
    //car les services ne sont pas instanciés de la même manière que les components. 
    readonly  personUrl = "http://localhost:8081/person/";
    readonly  personsUrl = "http://localhost:8081/persons/";
    
    constructor(private httpClient: HttpClient, private router:Router){

    }

    getAllPersons(): Observable<Person[]> {
        return this.httpClient.get<Person[]>(this.personsUrl);
    }

    getPersonById(id:number): Observable<Person> {
      return this.httpClient.get<Person>(this.personUrl + id);
  }
    
    delete(id: number):Observable<object> {
        return this.httpClient.delete(this.personUrl + id);
     }

     update(person:Person):Observable<Person>{
        return this.httpClient.put<Person>(this.personUrl, person);
     }

     create(person:Person):Observable<Person>{
        return this.httpClient.post<Person>(this.personUrl, person);
     } 

     handleError( message:string, person:Person){
        console.log("Error while " + message + " " + person);
     }
}