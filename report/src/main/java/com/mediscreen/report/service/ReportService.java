package com.mediscreen.report.service;

import com.mediscreen.report.exception.NotFoundException;
import com.mediscreen.report.model.Report;
import org.springframework.http.ResponseEntity;

public interface ReportService {
    /**
     * Generates a medical report for given patient
     * @param id patient identifier
     * @return the generated report
     * @throws NotFoundException
     */
    Report generateReport(Integer id) throws NotFoundException;
}
