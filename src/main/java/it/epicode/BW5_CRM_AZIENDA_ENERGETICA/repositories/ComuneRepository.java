package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.repositories;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.entities.Comune;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComuneRepository extends JpaRepository<Comune, Long> {
    Comune findByDenominazione(String denominazione);
}
