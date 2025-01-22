package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.runner;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services.ComuneService;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services.IndirizzoService;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.dto.IndirizzoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class IndirizziRunner implements CommandLineRunner {
    @Autowired
    private ComuneService comuneService;

    @Autowired
    private IndirizzoService indirizzoService;

    @Override
    public void run(String... args) throws Exception {
        IndirizzoRequest indirizzo = new IndirizzoRequest();
        indirizzo.setVia("Via le mani dal naso");
        indirizzo.setComuneId(35L);
        indirizzo.setCap("65435");
        indirizzo.setLocalita("casa de Fede");
        indirizzo.setCivico("45");
        indirizzoService.save(indirizzo);
    }
}
