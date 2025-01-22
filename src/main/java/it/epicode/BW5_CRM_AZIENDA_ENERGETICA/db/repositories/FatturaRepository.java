package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.repositories;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Fattura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FatturaRepository extends JpaRepository<Fattura, Long> {
}
