package fr.apptrade.backend.api.v1.config.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private String message;
    private int status;
    private String error;
    private String exception;
    private Instant timestamp;
}


