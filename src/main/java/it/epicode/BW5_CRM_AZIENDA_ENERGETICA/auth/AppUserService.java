package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.auth;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.exceptions.ConflictException;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.exceptions.UnauthorizedException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public AppUser registerUser(String username, String password, Set<Role> roles, String nome, String cognome, String email) {
        // Verifica se l'username o l'email esistono già
        if (appUserRepository.findByUsername(username).isPresent()) {
            throw new ConflictException("Username già in uso");
        }

        if (appUserRepository.findByEmail(email).isPresent()) {
            throw new ConflictException("Email già in uso");
        }

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roles);
        user.setNome(nome);
        user.setCognome(cognome);
        user.setEmail(email);

        return appUserRepository.save(user);
    }

    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public AuthResponse authenticateUser(String username, String password) {
        try {
            // Autenticazione con AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // Recupera i dettagli dell'utente autenticato
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Genera il token JWT
            String token = jwtTokenUtil.generateToken(userDetails);

            // Recupera l'oggetto AppUser dal database
            AppUser user = loadUserByUsername(username);

            // Restituisce il token e l'utente
            return new AuthResponse(token, user);

        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Credenziali non valide.");
        } catch (AuthenticationException e) {
            throw new UnauthorizedException("Errore nell'autenticazione: " + e.getMessage());
        }
    }

    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato con username: " + username));
    }

    public AppUser save(AppUser appUser) {
        try {
            return appUserRepository.save(appUser);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Dati non validi o duplicati: " + e.getMessage());
        }
    }

}
