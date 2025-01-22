package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Cliente;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Fattura;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.repositories.FatturaRepository;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.exceptions.BadRequestException;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.exceptions.InternalServerErrorException;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.exceptions.ResourceNotFoundException;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.dto.FatturaRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FatturaService {
    @Autowired
    FatturaRepository fatturaRepository;

    @Autowired
    ClienteService clienteService;

    @Transactional
    public Fattura save(FatturaRequest newFattura){
        Fattura fattura = new Fattura();
        BeanUtils.copyProperties(newFattura, fattura);
        Cliente cliente = clienteService.findById(newFattura.getClienteId());
        fattura.setCliente(cliente);

        return fatturaRepository.save(fattura);
    }

    public Fattura findById(Long id) {
        if (id == null) {
            throw new BadRequestException("ID indirizzo non valido.");
        }

        return fatturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fattura con ID " + id + " non trovato."));
    }

    public Page<Fattura> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return fatturaRepository.findAll(pageable);
    }

    public Fattura delete(Long id) {
        if (id == null) {
            throw new BadRequestException("ID indirizzo non valido.");
        }

        Fattura indirizzo = findById(id); // Usa il metodo con gestione dell'eccezione
        try {
            fatturaRepository.delete(indirizzo);
            return indirizzo;
        } catch (Exception e) {
            throw new InternalServerErrorException("Errore durante la cancellazione dell'indirizzo: " + e.getMessage());
        }
    }
}
