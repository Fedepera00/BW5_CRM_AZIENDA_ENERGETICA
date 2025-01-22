package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.dto;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.enums.TipoCliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import org.apache.logging.log4j.message.Message;

import java.time.LocalDate;
import java.util.List;

@Data
public class ClienteRequest {

    @Email (message = "Email non valida")
    @NotNull(message = "Il campo Email non può essere vuoto")
    private String email;

    @NotBlank (message = "Il campo RagioneSociale non può essere vuoto")
    private String ragioneSociale;

    @NotBlank (message = "Il campo partitaIva non può essere vuoto")
    private String partitaIva;

    @NotNull (message = "Il campo dataInserimento non può essere vuoto")
    @FutureOrPresent (message = "Il campo dataInserimento non può essere al passato")
    private LocalDate dataInserimento;

    @NotNull (message = "Il campo dataUltimoContatto non può essere vuoto")
    private LocalDate dataUltimoContatto;

    @NotNull (message = "Il campo fatturatoAnnuale non può essere vuoto")
    private Long fatturatoAnnuale;

    @NotBlank (message = "Il campo pec non può essere vuoto")
    @Email (message = "Email non valida")
    private String pec;

    @NotBlank (message = "Il campo telefono non può essere vuoto")

    private String telefono;

    @NotBlank (message = "Il campo emailContatto non può essere vuoto")
    @Email (message = "Email non valida")
    private String emailContatto;

    @NotBlank (message = "Il campo nomeContatto non può essere vuoto")
    private String nomeContatto;

    @NotBlank (message = "Il campo cognomeContatto non può essere vuoto")
    private String cognomeContatto;

    @NotBlank (message = "Il campo telefonoContatto non può essere vuoto")
    private String TelefonoContatto;

    @NotBlank (message = "Il campo logoAziendale non può essere vuoto")
    private String logoAziendale;

    @NotNull (message = "Il campo indirizziIds non può essere vuoto")
    private List<Long> indirizziIds;

    @NotNull (message = "Il campo tipoCliente non può essere vuoto")
    private TipoCliente tipoCliente;

}
