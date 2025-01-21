package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String nome;
    private String cognome;
    private String email;
}