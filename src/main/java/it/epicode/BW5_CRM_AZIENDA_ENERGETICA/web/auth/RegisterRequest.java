package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
}
