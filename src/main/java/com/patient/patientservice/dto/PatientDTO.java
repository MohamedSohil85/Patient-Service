package com.patient.patientservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.patient.patientservice.enurmation.Gender;
import jakarta.validation.constraints.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

    @Size(min = 3,max = 10,message = "first name is invalid")
    @NotBlank(message = "Please enter first name")
    @Pattern(regexp = "(\"^.*[A-Z].*[a-z].*$\")",message = "first Letter must be Capital")
    private String firstName;
    @Size(min = 3,max = 10,message = "last name is invalid")
    @NotBlank(message = "Please enter last name")
    @Pattern(regexp = "(\"^.*[A-Z].*[a-z].*$\")",message = "first Letter must be Capital")
    private String lastName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Past
    private LocalDate dateOfBirth;
    private Gender gender;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm a")
    private LocalDateTime dateOfRegistration;
    private String nationality;
    @NotEmpty
    private String phoneNumber;
    @Email(message = "Please enter valid email", regexp="^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}")
    private String email;
    @NotEmpty
    private String city;
    @NotEmpty
    private String state;
    @NotEmpty
    private String street;
    @NotEmpty
    private String country;
    @NotNull
    @Positive
    private int zipcode;
}
