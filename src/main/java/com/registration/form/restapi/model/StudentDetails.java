package com.registration.form.restapi.model;


import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class StudentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String gender;
    private String phoneNumber;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String country;
    private String emailAddress;
    private String password;
}
