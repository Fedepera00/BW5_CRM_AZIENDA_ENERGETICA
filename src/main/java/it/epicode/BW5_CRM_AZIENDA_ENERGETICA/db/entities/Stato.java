package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Stato {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


}