package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.enums.TipoCliente;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "clienti")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String email;

    private String ragioneSociale;

    private String partitaIva;

    private LocalDate dataInserimento;

    private LocalDate dataUltimoContatto;

    private Long fatturatoAnnuale;

    private String pec;

    private String telefono;

    private String emailContatto;

    private String nomeContatto;

    private String cognomeContatto;

    private String TelefonoContatto;

    private String logoAziendale;

    @OneToOne
    SocietaLogo societaLogo;

    @OneToMany
    private List<Indirizzo> indirizzi;

    @Enumerated (EnumType.STRING)
    private TipoCliente tipoCliente;
}
