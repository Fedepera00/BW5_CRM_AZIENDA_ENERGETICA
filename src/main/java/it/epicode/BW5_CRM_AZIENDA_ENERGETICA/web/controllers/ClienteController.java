package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.controllers;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Cliente;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services.ClienteService;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services.UserRoleService;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.dto.ClienteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cliente")
@PreAuthorize("isAuthenticated()")
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @Autowired
    UserRoleService userRoleService;

    @GetMapping("/paged")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<Cliente>> findAll(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        String username = userRoleService.getUsernameForAll(user);
        return ResponseEntity.ok(clienteService.findAll(page, size, sortBy));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Cliente> findById(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user, @RequestParam Long id) {
        // Verifica l'utente e il ruolo tramite il servizio
        String username = userRoleService.getUsernameForAll(user);

        return ResponseEntity.ok(clienteService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Cliente> update(@RequestBody ClienteRequest newCliente, @RequestParam Long id, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        String username = userRoleService.getUsernameForAdmin(user);

        return ResponseEntity.ok(clienteService.update(id, newCliente));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Cliente> save(@RequestBody ClienteRequest newCliente, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        String username = userRoleService.getUsernameForAdmin(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(newCliente));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Cliente> delete(@RequestParam Long id, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user){
        String username = userRoleService.getUsernameForAdmin(user);

        return ResponseEntity.ok(clienteService.delete(id));
    }
}


