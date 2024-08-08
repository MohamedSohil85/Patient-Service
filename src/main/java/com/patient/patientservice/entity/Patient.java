package com.patient.patientservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.patient.patientservice.enurmation.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long patientId;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm a")
    private LocalDateTime dateOfRegistration;
    private String nationality;
    private String phoneNumber;
    private String email;
    private String  insuranceProvider;
    @OneToOne
    private Address address;
}
