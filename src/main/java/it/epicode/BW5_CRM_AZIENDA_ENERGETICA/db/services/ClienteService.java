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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    IndirizzoService indirizzoService;

    @Autowired
    AppUserRepository appUserRepository;

    public Cliente save(ClienteRequest newCliente) {
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

    public Cliente update(Long id, ClienteRequest newCliente) {
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

}
