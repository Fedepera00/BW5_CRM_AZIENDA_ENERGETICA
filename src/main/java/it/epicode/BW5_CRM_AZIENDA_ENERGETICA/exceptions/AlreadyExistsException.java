package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.exceptions;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException() {
    }
    public AlreadyExistsException(String message) {
        super(message);
    }
}
