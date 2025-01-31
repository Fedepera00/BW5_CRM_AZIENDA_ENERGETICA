package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.controllers;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Fattura;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services.FatturaService;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services.UserRoleService;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.dto.FatturaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fattura")
@PreAuthorize("isAuthenticated()")
public class FatturaController {
    @Autowired
    FatturaService fatturaService;

    @Autowired
    UserRoleService userRoleService;

    @GetMapping("/paged")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<Fattura>> findAll(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        String username = user.getUsername();

        userRoleService.getUsernameForAll(username);
        return ResponseEntity.ok(fatturaService.findAll(page, size, sortBy));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Fattura> findById(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user, @PathVariable Long id) {
        // Verifica l'utente e il ruolo tramite il servizio
        String username = user.getUsername();

        userRoleService.getUsernameForAll(username);

        return ResponseEntity.ok(fatturaService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Fattura> save(@RequestBody FatturaRequest newFattura, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        String username = user.getUsername();

        userRoleService.getUsernameForAdmin(username);

        return ResponseEntity.status(HttpStatus.CREATED).body(fatturaService.save(newFattura));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Fattura> delete(@PathVariable Long id, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user){
        String username = user.getUsername();

        userRoleService.getUsernameForAdmin(username);

        return ResponseEntity.ok(fatturaService.delete(id));
    }
}
