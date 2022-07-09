package com.mediscreen.report.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Report {

    public Report(RiskLevel riskLevel, String patientFamilyName, String patientGivenName, int age, String sexe) {
        this.riskLevel = riskLevel;
        this.patientFamilyName = patientFamilyName;
        this.patientGivenName = patientGivenName;
        this.age = age;
        this.sexe = sexe;
    }

    public Report() {
    }

    private RiskLevel riskLevel;
    private String patientFamilyName;
    private String patientGivenName;
    private int age;
    private String sexe;
}
