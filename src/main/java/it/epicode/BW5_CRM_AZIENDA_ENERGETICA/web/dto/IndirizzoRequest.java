package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.dto;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Comune;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IndirizzoRequest {
    @NotBlank (message = "Il campo via non può essere vuoto")
    private String via;

    @NotBlank(message = "Il campo civico non può essere vuoto")
    private String civico;

    @NotBlank (message = "Il campo localita non può essere vuoto")
    private String localita;

    @NotBlank (message = "Il campo cap non può essere vuoto")
    private String cap;

    @NotNull (message = "Il campo comuneId non può essere vuoto")
    private Long comuneId;
}
