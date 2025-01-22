package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.exceptions;


import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorMessage {
    private String message;
    private HttpStatus statusCode;
}