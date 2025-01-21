package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ClienteRequest {

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

    private List<Long> indirizziIds;

}
