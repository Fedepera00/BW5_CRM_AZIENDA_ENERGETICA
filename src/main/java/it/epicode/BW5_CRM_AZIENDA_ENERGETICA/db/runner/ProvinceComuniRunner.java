package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.runner;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services.ComuneService;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProvinceComuniRunner implements CommandLineRunner {

    @Autowired
    private ComuneService comuneService;

    @Autowired
    private ProvinciaService provinciaService;

    private String provinceFilePath = "src/main/resources/assets/province-italiane.csv";


    private String comuniFilePath = "src/main/resources/assets/comuni-italiani.csv";


    @Override
    public void run(String... args) {
        try {
            if (provinciaService.findAll().isEmpty())
                provinciaService.importaProvince(provinceFilePath);
            System.out.println("Importazione province completata.");


            if (comuneService.findAll().isEmpty())
                comuneService.importaComuni(comuniFilePath);
            System.out.println("Importazione comuni completata.");
        } catch (IOException e) {
            System.err.println("Errore durante l'importazione: " + e.getMessage());
        }
    }
}