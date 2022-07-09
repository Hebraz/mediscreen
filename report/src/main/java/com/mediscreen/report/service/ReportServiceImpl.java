package com.mediscreen.report.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.report.exception.NotFoundException;
import com.mediscreen.report.model.Note;
import com.mediscreen.report.model.Person;
import com.mediscreen.report.model.Report;
import com.mediscreen.report.model.RiskLevel;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final RestTemplate restTemplate;
    @Value("${mediscreen-person-api-url}")
    private String personApiUrl;
    @Value("${mediscreen-notes-api-url}")
    private String noteApiUrl;
    @Autowired
    public ReportServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Generates a medical report for given patient
     *
     * @param id patient identifier
     * @return the generated report
     * @throws NotFoundException
     */
    public Report generateReport(Integer id) throws NotFoundException{
        Person person = this.getPerson(id);
        List<Note> notes = this.getNotes(id);
        RiskLevel riskLevel = calculateRisk(person, notes);
        return new Report(riskLevel, person.getFamilyName(), person.getGivenName(), person.getAge(), person.getSex());
    }

    /**
     * Calculates risk level according to age of patient and notes
     * @param person
     * @param notes
     * @return risk level
     */
    public RiskLevel calculateRisk(Person person, List<Note> notes) {
        int nbTriggers = getNbTrigger(notes);
        int age = person.getAge();
        String sex = person.getSex();

        if(age>30){
            if(nbTriggers<2){
                return RiskLevel.NONE;
            } else if (nbTriggers<6){
                return RiskLevel.BORDERLINE;
            } else if (nbTriggers<8){
                return RiskLevel.IN_DANGER;
            } else {
                return RiskLevel.EARLY_ONSET;
            }
        } else {
            if(sex.equals("H")){
                if(nbTriggers == 0){
                    return  RiskLevel.NONE;
                } else if(nbTriggers<3){
                    return RiskLevel.BORDERLINE;
                } else if (nbTriggers<5){
                    return RiskLevel.IN_DANGER;
                } else {
                    return RiskLevel.EARLY_ONSET;
                }
            } else {
                if(nbTriggers == 0){
                    return  RiskLevel.NONE;
                } else if(nbTriggers<4){
                    return RiskLevel.BORDERLINE;
                } else if (nbTriggers<7){
                    return RiskLevel.IN_DANGER;
                } else {
                    return RiskLevel.EARLY_ONSET;
                }
            }
        }
    }

    /**
     * Gets the number of matched triggers in a list of notes
     * @param notes
     * @return number of triggers
     */
    public int getNbTrigger(List<Note> notes){

        int nbTrigger = 0;

        //Concat notes to look for hints
        String concatNotes = notes.stream().map(Note::getDescription).map(String::toLowerCase).collect(Collectors.joining());

        String [] keys = {
                "Hémoglobine A1C",
                "Microalbumine",
                "Taille",
                "Poids",
                "Fumeur",
                "Anormal",
                "Cholestérol",
                "Vertige",
                "Rechute",
                "Réaction",
                "Anticorps"
        };

        for(String key : keys){
            if(concatNotes.contains(key.toLowerCase(Locale.ROOT))){
                nbTrigger++;
            }
        }
        return nbTrigger;
    }

    /**
     * Gets the person info from api-person
     * @param id identifier of the patient
     * @return person information
     * @throws NotFoundException
     */
    public Person getPerson(Integer id) throws NotFoundException{

        String uri = personApiUrl + id;
        try{
            ResponseEntity<Person> responseEntity  = restTemplate.getForEntity(uri, Person.class);
            return  responseEntity.getBody();
        } catch (Exception e){
            throw new NotFoundException(e.getMessage());
        }
    }

    /**
     * Gets the notes of a patient from api-notes
     * @param id identifier of the patient
     * @return list of notes for the given patient
     * @throws NotFoundException
     */
    public List<Note> getNotes(Integer id) throws NotFoundException{

        String uri = noteApiUrl +id;
        try{
            ResponseEntity<Note[]> responseEntity = restTemplate.getForEntity(uri, Note[].class);
            if(responseEntity.getBody() != null){
                return Arrays.stream(responseEntity.getBody())
                        .collect(Collectors.toList());
            } else {
                return new ArrayList<>();
            }

        } catch (Exception e){
            throw new NotFoundException(e.getMessage());
        }
    }
}
