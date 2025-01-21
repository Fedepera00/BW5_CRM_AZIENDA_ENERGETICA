package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "province")
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String sigla;
    private String nome;
    private String regione;
}