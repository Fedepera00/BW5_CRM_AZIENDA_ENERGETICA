package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
