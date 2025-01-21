package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.repositories;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
