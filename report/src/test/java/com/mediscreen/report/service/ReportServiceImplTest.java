package com.mediscreen.report.service;

import com.mediscreen.report.exception.NotFoundException;
import com.mediscreen.report.model.Note;
import com.mediscreen.report.model.Person;
import com.mediscreen.report.model.RiskLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    private ReportServiceImpl reportService;

    @Mock
    RestTemplate restTemplate;

    @BeforeEach
    void initTest(){
        reportService = new ReportServiceImpl(restTemplate);
    }
    @Test
    void generateReport() {
    }


    @ParameterizedTest
    @MethodSource("generateCalculateRiskData")
    void calculateRisk(Person person, List<Note> notes, RiskLevel expectedRiskLevel)  {

        RiskLevel calculatedRisk = reportService.calculateRisk(person,notes);
        assertEquals(expectedRiskLevel,calculatedRisk);
    }

    static Stream<Arguments> generateCalculateRiskData() {
        Calendar age30 = Calendar.getInstance();
        Calendar age31 = Calendar.getInstance();
        age30.add(Calendar.YEAR, -30);
        age31.add(Calendar.YEAR, -31);
        return Stream.of(
                /*No notes => NONE*/
                Arguments.of(
                        Person.builder().birthdate(age30).sex("H").build(),
                        Arrays.asList(),
                        RiskLevel.NONE),
                /*  > 30y and 2 triggers => BORDERLINE*/
                Arguments.of(
                        Person.builder().birthdate(age31).sex("F").build(),
                        Arrays.asList(
                                Note.builder().description("qjkfhd Hémoglobine A1C qjslfho zierozj sjlk").build(),
                                Note.builder().description("dfsz Anticorps").build(),
                                Note.builder().description("dsqfd dfqdsfq dsfsq").build()),
                        RiskLevel.BORDERLINE),
                /* H <= 30y and 3 triggers => DANGER*/
                Arguments.of(
                        Person.builder().birthdate(age30).sex("H").build(),
                        Arrays.asList(
                                Note.builder().description("qjkfhd Microalbumine A1C qjslfho zierozj sjlk").build(),
                                Note.builder().description("dfsz Réaction").build(),
                                Note.builder().description("dsqfd Cholestérol dsfsq").build()),
                        RiskLevel.IN_DANGER),
                /* F <= 30y and 4 triggers => DANGER*/
                Arguments.of(
                        Person.builder().birthdate(age30).sex("F").build(),
                        Arrays.asList(
                                Note.builder().description("Poids Microalbumine A1C Taille Rechute sjlk").build()),
                        RiskLevel.IN_DANGER),
                /* H/F > 30y and 6 triggers => DANGER*/
                Arguments.of(
                        Person.builder().birthdate(age31).sex("F").build(),
                        Arrays.asList(
                                Note.builder().description("qjkfhd Microalbumine A1C qjslfho Taille sjlk").build(),
                                Note.builder().description("dfsz Réaction Anticorps").build(),
                                Note.builder().description("dsqfd Cholestérol Anormal").build()),
                        RiskLevel.IN_DANGER),
                /* H <= 30y and 6 triggers => DANGER*/
                Arguments.of(
                        Person.builder().birthdate(age30).sex("H").build(),
                        Arrays.asList(
                                Note.builder().description("qjkfhd Microalbumine A1C qjslfho Taille sjlk").build(),
                                Note.builder().description("dfsz Réaction Anticorps").build(),
                                Note.builder().description("Vertige dsqfd Cholestérol ").build()),
                        RiskLevel.EARLY_ONSET),
                /* F <= 30y and 7 triggers => DANGER*/
                Arguments.of(
                        Person.builder().birthdate(age30).sex("H").build(),
                        Arrays.asList(
                                Note.builder().description("qjkfhd Microalbumine A1C qjslfho Taille sjlk").build(),
                                Note.builder().description("Vertige dfsz Réaction Anticorps").build(),
                                Note.builder().description("dsqfd Cholestérol Hémoglobine A1C").build()),
                        RiskLevel.EARLY_ONSET),
                /* H/F > 30y and 8 triggers => DANGER*/
                Arguments.of(
                        Person.builder().birthdate(age31).sex("H").build(),
                        Arrays.asList(
                                Note.builder().description("qjkfhd Microalbumine A1C qjslfho Taille sjlk").build(),
                                Note.builder().description("Vertige dfsz Réaction Anticorps").build(),
                                Note.builder().description("dsqfd Cholestérol Hémoglobine A1C").build(),
                                Note.builder().description("Fumeur").build()),
                        RiskLevel.EARLY_ONSET)
        );
    }


    @ParameterizedTest
    @MethodSource("generatGetNbTriggerData")
    void getNbTrigger(List<Note> notes, int expectedNbTriggers) {
        int nbTriggers = reportService.getNbTrigger(notes);
        assertEquals(expectedNbTriggers,nbTriggers);
    }

    static Stream<Arguments> generatGetNbTriggerData() {
        return Stream.of(
                /*None => 0*/
                Arguments.of(
                        Arrays.asList(
                                Note.builder().description("qjkfhd zerzatt arzrza qjslfho zierozj sjlk").build(),
                                Note.builder().description("dfsz  A1C").build(),
                                Note.builder().description("dsqfd albumine dsfsq").build()),
                        0),
                /*Hémoglobine A1C => 1*/
                Arguments.of(
                        Arrays.asList(
                                Note.builder().description("qjkfhd zerzatt arzrza qjslfho zierozj sjlk").build(),
                                Note.builder().description("dfsz Hémoglobine A1C").build(),
                                Note.builder().description("dsqfd dfqdsfq dsfsq").build()),
                        1),
                /*Microalbumine, Taille, Poids x 2 => 3*/
                Arguments.of(
                        Arrays.asList(
                                Note.builder().description("Microalbumine zerzatt arzrza qjslfho zierozj sjlk").build(),
                                Note.builder().description("dfsz Taille A1C").build(),
                                Note.builder().description("dsqfd Poids dsfsq").build(),
                                Note.builder().description("dsqfd Poids dsfsq").build()),
                        3),
                /*Fumeur, Anormal, Cholestérol, Vertige, Rechute, Réaction, Anticorps => 7*/
                Arguments.of(
                        Arrays.asList(
                                Note.builder().description("Fumeur zerzatt Anormal qjslfho Cholestérol sjlk").build(),
                                Note.builder().description("dfsz Vertige A1C").build(),
                                Note.builder().description("Rechute Réaction dsfsq").build(),
                                Note.builder().description("dsqfd Anticorps dsfsq").build()),
                        7)
        );
    }

    @Test
    void getPersonOk() throws NotFoundException {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<Person> responseEntity = new ResponseEntity<Person>(
                Person.builder()
                        .id(1)
                        .familyName("Paul")
                        .givenName("Pierre")
                        .address("10 rue du cerisier, 75000 PARIS")
                        .phone("0606060606")
                        .sex("H")
                        .build(),
                header,
                HttpStatus.OK
        );

        when(restTemplate.getForEntity("http://localhost:8081/person/1",Person.class)).thenReturn(responseEntity);
        ReflectionTestUtils.setField(reportService, "personApiUrl","http://localhost:8081/person/");
        Person returnedPerson = reportService.getPerson(1);

        assertEquals(1, returnedPerson.getId());
        assertEquals("Paul", returnedPerson.getFamilyName());
        assertEquals("Pierre", returnedPerson.getGivenName());
        assertEquals("10 rue du cerisier, 75000 PARIS", returnedPerson.getAddress());
        assertEquals("0606060606", returnedPerson.getPhone());
        assertEquals("H", returnedPerson.getSex());
    }

    @Test
    void getPersonNotFound() throws NotFoundException {

        when(restTemplate.getForEntity("http://localhost:8081/person/3",Person.class)).thenThrow(new RuntimeException());
        ReflectionTestUtils.setField(reportService, "personApiUrl","http://localhost:8081/person/");
        assertThrows(NotFoundException.class,() -> reportService.getPerson(3));
    }

    @Test
    void getNotesOk() throws NotFoundException {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<Note[]> responseEntity = new ResponseEntity<Note[]>(
                new Note[]{
                        Note.builder().description("Note1").build(),
                        Note.builder().description("Note2").build(),
                        Note.builder().description("Note3").build()
                },
                header,
                HttpStatus.OK
        );

        when(restTemplate.getForEntity("http://localhost:8082/patHistory/1",Note[].class)).thenReturn(responseEntity);
        ReflectionTestUtils.setField(reportService, "noteApiUrl","http://localhost:8082/patHistory/");
        List<Note> returnedNotes = reportService.getNotes(1);

        assertEquals(3, returnedNotes.size());
        assertEquals("Note1", returnedNotes.get(0).getDescription());
        assertEquals("Note2", returnedNotes.get(1).getDescription());
        assertEquals("Note3", returnedNotes.get(2).getDescription());
    }

    @Test
    void getNotesNotFound() throws NotFoundException {

        when(restTemplate.getForEntity("http://localhost:8082/patHistory/1",Note[].class)).thenThrow(new RuntimeException());
        ReflectionTestUtils.setField(reportService, "noteApiUrl","http://localhost:8082/patHistory/");
        assertThrows(NotFoundException.class,() -> reportService.getNotes(3));
    }
}