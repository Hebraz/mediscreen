package com.mediscreen.report.controller;

import com.mediscreen.report.exception.NotFoundException;
import com.mediscreen.report.model.Report;
import com.mediscreen.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(maxAge = 3600)
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     *
     * @param id - id of the patien
     * @return
     */
    @GetMapping("/report/{id}")
    public ResponseEntity<Report> getReport(@PathVariable("id") final Integer id) throws NotFoundException {
        return ResponseEntity.ok( reportService.generateReport(id));
    }
}
