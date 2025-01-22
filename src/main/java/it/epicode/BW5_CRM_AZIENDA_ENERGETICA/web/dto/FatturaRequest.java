package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.dto;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.enums.Stato;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FatturaRequest {
    private LocalDate data;

    private Long importo;

    private String numeroFattura;

    private Stato stato;
}
