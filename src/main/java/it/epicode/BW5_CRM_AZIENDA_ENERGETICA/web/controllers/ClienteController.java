package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.controllers;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Cliente;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@PreAuthorize("isAuthenticated()")
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> findAll (){
        return ResponseEntity.ok(clienteService.findAll());
    }




}
