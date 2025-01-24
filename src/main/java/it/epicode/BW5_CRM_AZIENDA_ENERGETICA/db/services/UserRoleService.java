package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.auth.AppUser;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.auth.AppUserRepository;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.auth.Role;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    @Autowired
    AppUserRepository appUserRepository;


    public String getUsernameForAll(String username) {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("Utente non trovato"));

        if (appUser.getRoles().contains(Role.ROLE_USER) || appUser.getRoles().contains(Role.ROLE_ADMIN)) {
            return appUser.getUsername();
        } else {
            throw new UnauthorizedException("Accesso negato: l'utente non ha i privilegi necessari");
        }
    }

    public String getUsernameForAdmin(String username) {
        // Recupera l'utente AppUser dal database usando lo username
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("Utente non trovato"));

        // Controlla il ruolo dell'utente
        if (appUser.getRoles().contains(Role.ROLE_ADMIN)) {
            return appUser.getUsername();
        } else {
            throw new UnauthorizedException("Accesso negato: l'utente non ha i privilegi necessari");
        }
    }
}
