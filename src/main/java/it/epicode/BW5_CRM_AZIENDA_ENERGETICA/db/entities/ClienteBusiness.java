package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "clienti_business")
public class ClienteBusiness {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String ragioneSociale;

    private String email;

    private String partitaIva;

    private LocalDate dataInserimento;

    private LocalDate dataUltimoContatto;

    private Long fatturatoAnnuale;

    private String pec;

    private String telefono;

    private String emailContatto;

    private String nomeContatto;

    private String cognomeContatto;

    private String telefonoContatto;

    private String logoAziendale;

    @OneToMany
    List<Indirizzo> indirizzi;

}
