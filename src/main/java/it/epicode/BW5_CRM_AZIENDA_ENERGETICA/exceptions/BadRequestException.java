package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
