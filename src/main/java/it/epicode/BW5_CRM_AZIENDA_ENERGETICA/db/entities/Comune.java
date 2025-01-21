package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "comuni")
public class Comune {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String codiceProvincia;
    private String progressivo;
    private String denominazione;

    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;
}