package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.enums.Stato;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "fatture")
public class Fattura {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDate data;

    private Long importo;

    private String numeroFattura;

    @Enumerated (EnumType.STRING)
    private Stato stato;

    @ManyToOne
    private Cliente cliente;
}
