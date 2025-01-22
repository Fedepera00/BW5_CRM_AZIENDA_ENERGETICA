package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.dto;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.enums.Stato;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class FatturaRequest {

    @NotNull (message = "Il campo data non può essere vuoto")
    @FutureOrPresent (message = "Il campo data non può essere al passato")
    private LocalDate data;

    @NotNull (message = "Il campo importo non può essere vuoto")
    private Long importo;

    @NotBlank (message = "Il campo numeroFatture non può essere vuoto")
    private String numeroFattura;

   @NotNull (message = "Il campo stato non può essere vuoto")
    private Stato stato;

   @NotNull (message = "Il campo ClienteId non può essere vuoto")
    private Long ClienteId;
}
