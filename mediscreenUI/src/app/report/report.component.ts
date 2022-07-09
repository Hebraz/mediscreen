import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { Report } from '../models/report-model';
import { ReportService } from '../services/report.service';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit {

  report$ !:Observable<Report>;

  constructor(
    private reportService: ReportService,
    private route: ActivatedRoute,
  ) { 

  }

  ngOnInit(): void {
    var id = +this.route.snapshot.params['id'];
    this.report$ = this.reportService.getReport(id);
  }

}
