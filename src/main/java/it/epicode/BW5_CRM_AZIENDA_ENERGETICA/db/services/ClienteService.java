package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.auth.AppUser;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.auth.AppUserRepository;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.auth.Role;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Cliente;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Indirizzo;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.repositories.ClienteRepository;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.exceptions.ResourceNotFoundException;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.exceptions.UnauthorizedException;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.dto.ClienteRequest;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
@Validated
@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    IndirizzoService indirizzoService;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    CloudinaryService cloudinaryService;

    public Cliente save(@Valid ClienteRequest newCliente) {
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(newCliente, cliente);

        // Verifica se la lista degli indirizzi è nulla, e se lo è, inizializzala come una nuova lista
        if (cliente.getIndirizzi() == null) {
            cliente.setIndirizzi(new ArrayList<>());
        }

        if (newCliente.getIndirizziIds() != null && !newCliente.getIndirizziIds().isEmpty()) {
            for (Long indirizzoId : newCliente.getIndirizziIds()) {
                try {
                    // Cerca l'indirizzo per ID. Se non esiste, verrà lanciata una ResourceNotFoundException
                    Indirizzo indirizzo = indirizzoService.findById(indirizzoId);
                    cliente.getIndirizzi().add(indirizzo);
                } catch (ResourceNotFoundException ex) {
                    // Gestisci il caso in cui l'indirizzo non sia trovato
                    throw new ResourceNotFoundException("Indirizzo con ID " + indirizzoId + " non trovato.");
                }
            }
        }
        return clienteRepository.save(cliente);
    }

    public Page<Cliente> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return clienteRepository.findAll(pageable);
    }

    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + id + " non trovato"));
    }

    public Cliente update(Long id,@Valid ClienteRequest newCliente) {
        Cliente c = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + id + " non trovato"));

        BeanUtils.copyProperties(newCliente, c);

        if (newCliente.getIndirizziIds() != null && !newCliente.getIndirizziIds().isEmpty()) {
            for (Long indirizzoId : newCliente.getIndirizziIds()) {
                Indirizzo indirizzo = indirizzoService.findById(indirizzoId);
                if (indirizzo != null) {
                    if (!c.getIndirizzi().contains(indirizzo)) {
                        c.getIndirizzi().add(indirizzo);
                    }
                } else {
                    throw new ResourceNotFoundException("Indirizzo con ID " + indirizzoId + " non trovato.");
                }
            }
        }
        return clienteRepository.save(c);
    }

    public Cliente delete(Long id) {
        Cliente c = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + id + " non trovato"));
        clienteRepository.delete(c);
        return c;
    }

    public String uploadLogo(Long clienteId, MultipartFile logoFile) {
        if (logoFile == null || logoFile.isEmpty()) {
            throw new IllegalArgumentException("Il file logo non può essere vuoto");
        }

        // Recupera il cliente dal database
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + clienteId + " non trovato"));

        try {
            // Upload del logo su Cloudinary
            String logoUrl = cloudinaryService.uploadImage(logoFile);

            // Associa l'URL al campo logoAziendale del cliente
            cliente.setLogoAziendale(logoUrl);
            System.out.println("Cliente aggiornato: " + cliente);
            // Salva l'entità cliente aggiornata nel database
            clienteRepository.save(cliente);

            return logoUrl;
        } catch (IOException e) {
            throw new RuntimeException("Errore durante l'upload del logo: " + e.getMessage(), e);
        }
    }
}
