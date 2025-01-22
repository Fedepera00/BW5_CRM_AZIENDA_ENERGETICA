package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.repositories;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.SocietaLogo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocietaLogoRepository extends JpaRepository<SocietaLogo, Long> {
    SocietaLogo findByClienteId(Long clienteId);
}
