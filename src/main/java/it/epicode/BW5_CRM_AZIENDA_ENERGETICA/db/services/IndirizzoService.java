package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.auth.AppUser;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.auth.Role;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Cliente;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Comune;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Indirizzo;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.repositories.IndirizzoRepository;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.exceptions.BadRequestException;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.exceptions.InternalServerErrorException;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.exceptions.ResourceNotFoundException;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.exceptions.UnauthorizedException;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.dto.IndirizzoRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class IndirizzoService {
    @Autowired
    IndirizzoRepository indirizzoRepository;

    @Autowired
    ComuneService comuneService;

    @Transactional
    public Indirizzo save(IndirizzoRequest newIndirizzo) {
        if (newIndirizzo == null) {
            throw new BadRequestException("La richiesta per il nuovo indirizzo è nulla.");
        }

        Comune comune = comuneService.findById(newIndirizzo.getComuneId());
        if (comune == null) {
            throw new ResourceNotFoundException("Comune con ID " + newIndirizzo.getComuneId() + " non trovato.");
        }

        Indirizzo indirizzo = new Indirizzo();
        BeanUtils.copyProperties(newIndirizzo, indirizzo);
        indirizzo.setComune(comune);

        try {
            return indirizzoRepository.save(indirizzo);
        } catch (Exception e) {
            throw new InternalServerErrorException("Errore durante il salvataggio dell'indirizzo: " + e.getMessage());
        }
    }

    public Indirizzo findById(Long id) {
        if (id == null) {
            throw new BadRequestException("ID indirizzo non valido.");
        }

        return indirizzoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Indirizzo con ID " + id + " non trovato."));
    }

    public Page<Indirizzo> findAll(int page, int size, String sortBy) {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            return indirizzoRepository.findAll(pageable);
    }

    public Indirizzo update(Long id, IndirizzoRequest newIndirizzo) {
        if (id == null || newIndirizzo == null) {
            throw new BadRequestException("ID o richiesta indirizzo non validi.");
        }

        Indirizzo indirizzo = findById(id); // Usa il metodo con gestione dell'eccezione
        Comune comune = comuneService.findById(newIndirizzo.getComuneId());
        if (comune == null) {
            throw new ResourceNotFoundException("Comune con ID " + newIndirizzo.getComuneId() + " non trovato.");
        }

        BeanUtils.copyProperties(newIndirizzo, indirizzo);
        indirizzo.setComune(comune);

        try {
            return indirizzoRepository.save(indirizzo);
        } catch (Exception e) {
            throw new InternalServerErrorException("Errore durante l'aggiornamento dell'indirizzo: " + e.getMessage());
        }
    }

    public Indirizzo delete(Long id) {
        if (id == null) {
            throw new BadRequestException("ID indirizzo non valido.");
        }

        Indirizzo indirizzo = findById(id); // Usa il metodo con gestione dell'eccezione
        try {
            indirizzoRepository.delete(indirizzo);
            return indirizzo;
        } catch (Exception e) {
            throw new InternalServerErrorException("Errore durante la cancellazione dell'indirizzo: " + e.getMessage());
        }
    }
}