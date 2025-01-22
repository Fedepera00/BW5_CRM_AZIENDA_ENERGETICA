package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.controllers;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Indirizzo;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services.IndirizzoService;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services.UserRoleService;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.dto.IndirizzoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/indirizzo")
@PreAuthorize("isAuthenticated()")
public class IndirizzoController {
    @Autowired
    IndirizzoService indirizzoService;

    @Autowired
    UserRoleService userRoleService;

    @GetMapping("/paged")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<Indirizzo>> findAll(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        String username = userRoleService.getUsernameForAll(user);
        return ResponseEntity.ok(indirizzoService.findAll(page, size, sortBy));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Indirizzo> findById(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user, @RequestParam Long id) {
        // Verifica l'utente e il ruolo tramite il servizio
        String username = userRoleService.getUsernameForAll(user);

        return ResponseEntity.ok(indirizzoService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Indirizzo> update(@RequestBody IndirizzoRequest newIndirizzo, @RequestParam Long id, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        String username = userRoleService.getUsernameForAdmin(user);

        return ResponseEntity.ok(indirizzoService.update(id, newIndirizzo));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Indirizzo> save(@RequestBody IndirizzoRequest newIndirizzo, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        String username = userRoleService.getUsernameForAdmin(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(indirizzoService.save(newIndirizzo));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Indirizzo> delete(@RequestParam Long id, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user){
        String username = userRoleService.getUsernameForAdmin(user);

        return ResponseEntity.ok(indirizzoService.delete(id));
    }
}
