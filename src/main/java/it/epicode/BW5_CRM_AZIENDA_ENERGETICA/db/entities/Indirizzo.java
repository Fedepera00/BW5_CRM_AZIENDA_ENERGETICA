package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "indirizzi")
public class Indirizzo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private  String via;

    private String civico;

    private String cap;

    @OneToOne
    private Comune comune;
}
