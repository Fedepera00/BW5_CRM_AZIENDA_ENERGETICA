package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.exceptions;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }
}
