import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Observable } from "rxjs";
import { Report } from "../models/report-model";



@Injectable({
    providedIn: 'root'
})



export class ReportService{
    //un service n'a pas de méthode  ngOnInit(), 
    //car les services ne sont pas instanciés de la même manière que les components. 
    readonly  reportUrl = "http://localhost:8083/report/";
    
    constructor(private httpClient: HttpClient, private router:Router){

    }

    getReport(patientId:number): Observable<Report> {
        return this.httpClient.get<Report>(this.reportUrl + patientId.toString());
    }
}
