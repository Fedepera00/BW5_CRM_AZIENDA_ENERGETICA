package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.controllers;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.auth.Role;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Cliente;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@PreAuthorize("isAuthenticated()")
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Cliente>> findAll(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        // Verifica l'utente e il ruolo tramite il servizio
        String username = clienteService.getUsernameForAll(user);
        System.out.println("Utente autenticato: " + username);

        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Cliente> findById(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user, @RequestParam Long id) {
        // Verifica l'utente e il ruolo tramite il servizio
        String username = clienteService.getUsernameForAll(user);

        return ResponseEntity.ok(clienteService.findById(id));
    }
}

