package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.stream.Collectors;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Consente tutte le richieste su tutti gli endpoint
                        .allowedOrigins("*") // Consente richieste da qualsiasi origine
                        .allowedMethods("*") // Consente tutti i metodi (GET, POST, PUT, DELETE, PATCH, OPTIONS)
                        .allowedHeaders("*") // Consente tutti gli header
                        .allowCredentials(false); // Consente l'invio di credenziali, mettere false se allowedOrigins è "*"
            }
        };
    }

    @Service
    public static class CustomUserDetailsService implements UserDetailsService {

        @Autowired
        private AppUserRepository appUserRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            AppUser appUser = appUserRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato con username: " + username));

            return new User(
                    appUser.getUsername(),
                    appUser.getPassword(),
                    appUser.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.name()))
                            .collect(Collectors.toList())
            );
        }
    }
}
