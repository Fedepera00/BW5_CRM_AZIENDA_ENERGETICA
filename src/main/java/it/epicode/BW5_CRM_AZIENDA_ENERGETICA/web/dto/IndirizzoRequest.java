package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.dto;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Comune;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class IndirizzoRequest {
    private String via;

    private String civico;

    private String localita;

    private String cap;

    private Long comuneId;
}
