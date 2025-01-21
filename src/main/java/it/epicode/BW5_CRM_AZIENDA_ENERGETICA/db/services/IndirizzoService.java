package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Comune;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Indirizzo;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.repositories.IndirizzoRepository;
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
        return indirizzoRepository.findById(id).get();
    }

    public List<Indirizzo> findAll(){
        return indirizzoRepository.findAll();
    }

    public Indirizzo update(Long id, IndirizzoRequest newIndirizzo){
        Indirizzo indirizzo = indirizzoRepository.findById(id).get();
        Comune comune = comuneService.findById(newIndirizzo.getComuneId());
        BeanUtils.copyProperties(newIndirizzo, indirizzo);
        indirizzo.setComune(comune);

        return indirizzoRepository.save(indirizzo);
    }

    public Indirizzo delete(Long id){
        Indirizzo indirizzo = indirizzoRepository.findById(id).get();
        indirizzoRepository.delete(indirizzo);
        return indirizzo;
    }
}
