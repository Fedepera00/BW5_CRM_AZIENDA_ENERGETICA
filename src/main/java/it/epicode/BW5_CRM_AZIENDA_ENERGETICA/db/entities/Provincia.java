package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "province")
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nome;

    private String sigla;

    @OneToMany(mappedBy = "provincia", cascade = CascadeType.ALL)
    private List<Comune> comuni;
}
