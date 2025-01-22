package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Comune;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Indirizzo;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.repositories.IndirizzoRepository;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.exceptions.ResourceNotFoundException;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.dto.IndirizzoRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Indirizzo save(IndirizzoRequest newIndirzzo){
        Comune comune = comuneService.findById(newIndirzzo.getComuneId());

        Indirizzo indirizzo = new Indirizzo();
        BeanUtils.copyProperties(newIndirzzo, indirizzo);
        indirizzo.setComune(comune);

        return indirizzoRepository.save(indirizzo);
    }

    public Indirizzo findById(Long id){
        // Restituisce un Optional e gestisce il caso in cui l'indirizzo non esista
        return indirizzoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Indirizzo con ID " + id + " non trovato."));
    }

    public List<Indirizzo> findAll(){
        return indirizzoRepository.findAll();
    }

    public Indirizzo update(Long id, IndirizzoRequest newIndirizzo){
        Indirizzo indirizzo = findById(id); // Usa il nuovo metodo con gestione dell'eccezione
        Comune comune = comuneService.findById(newIndirizzo.getComuneId());
        BeanUtils.copyProperties(newIndirizzo, indirizzo);
        indirizzo.setComune(comune);

        return indirizzoRepository.save(indirizzo);
    }

    public Indirizzo delete(Long id){
        Indirizzo indirizzo = findById(id); // Usa il nuovo metodo con gestione dell'eccezione
        indirizzoRepository.delete(indirizzo);
        return indirizzo;
    }
}