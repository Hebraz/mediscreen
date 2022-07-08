package com.mediscreen.report.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;

@Getter
@Setter
@Builder
public class Person {
    private Integer id;
    private String familyName;
    private String givenName;
    private Calendar birthdate;
    private String address;
    private String phone;
    private String sex;

    public Person() {
    }

    public Person(Integer id, String familyName, String givenName, Calendar birthdate, String address, String phone, String sex) {
        this.id = id;
        this.familyName = familyName;
        this.givenName = givenName;
        this.birthdate = birthdate;
        this.address = address;
        this.phone = phone;
        this.sex = sex;
    }

    public int getAge(){
        LocalDate localDateBirth = this.birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDateNow = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(localDateBirth, localDateNow).getYears();
    }
}
