package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class AuthRunner implements ApplicationRunner {

    @Autowired
    private AppUserService appUserService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<AppUser> adminUser = appUserService.findByUsername("admin");
        if (adminUser.isEmpty()) {
            appUserService.registerUser(
                    "admin",
                    "adminpwd",
                    Set.of(Role.ROLE_ADMIN),
                    "Admin",  // Nome
                    "User",   // Cognome
                    "admin@example.com"  // Email
            );
        }

        Optional<AppUser> normalUser = appUserService.findByUsername("user");
        if (normalUser.isEmpty()) {
            appUserService.registerUser(
                    "user",
                    "userpwd",
                    Set.of(Role.ROLE_USER),
                    "Normal", // Nome
                    "User",   // Cognome
                    "user@example.com"  // Email
            );
        }
    }
}