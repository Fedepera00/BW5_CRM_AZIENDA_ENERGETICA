package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.repositories;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.ClienteBusiness;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteBusinessRepo extends JpaRepository<ClienteBusiness, Long> {
}
