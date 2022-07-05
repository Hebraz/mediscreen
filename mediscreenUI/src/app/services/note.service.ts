import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Person } from "../models/person.model";
import { catchError, Observable, timer } from "rxjs";
import { Router } from "@angular/router";
import { Note } from "../models/note-model";

@Injectable({
    providedIn: 'root'
})



export class NoteService{
    //un service n'a pas de méthode  ngOnInit(), 
    //car les services ne sont pas instanciés de la même manière que les components. 
    readonly  noteUrl = "http://localhost:8082/patHistory/";
    
    constructor(private httpClient: HttpClient, private router:Router){

    }

    getPatientNotes(patientId:number): Observable<Note[]> {
        return this.httpClient.get<Note[]>(this.noteUrl + patientId.toString());
    }

    create(note:Note):Observable<Note>{
        return this.httpClient.post<Note>(this.noteUrl, note);
    }

    update(note:Note):Observable<Note>{
        return this.httpClient.put<Note>(this.noteUrl, note);
    }
    
}