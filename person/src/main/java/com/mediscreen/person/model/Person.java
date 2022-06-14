package com.mediscreen.person.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Calendar;

@Entity
@Getter
@Setter
@Builder
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "family_name")
    @Size(max=50, message = "Family name must be at most 50 characters in length")
    private String familyName;

    @Column(name = "given_name")
    @Size(max=50, message = "Given name must be at most 50 characters in length")
    private String givenName;

    @Temporal(TemporalType.DATE)
    private Calendar birthdate;

    @Size(max=200, message = "Address name must be at most 200 characters in length")
    private String address;

    @Size(max=30, message = "Phone number must be at most 30 characters in length")
    private String phone;

    @Size(max=11, message = "Sex must be at most 11 characters in length")
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
}
