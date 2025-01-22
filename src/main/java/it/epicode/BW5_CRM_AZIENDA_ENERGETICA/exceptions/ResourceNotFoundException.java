package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
