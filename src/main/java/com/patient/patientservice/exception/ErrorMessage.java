package com.patient.patientservice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorMessage {

    private int statusCode;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime timestamp;
    private String message;
    private String description;
}
