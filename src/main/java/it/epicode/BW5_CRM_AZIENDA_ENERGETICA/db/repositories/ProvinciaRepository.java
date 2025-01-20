package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.repositories;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {
    Provincia findByNome(String nome);
    Provincia findBySigla(String sigla);
}
