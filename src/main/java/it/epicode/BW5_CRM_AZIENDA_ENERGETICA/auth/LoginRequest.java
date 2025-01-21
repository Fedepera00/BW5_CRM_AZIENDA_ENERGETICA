package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
