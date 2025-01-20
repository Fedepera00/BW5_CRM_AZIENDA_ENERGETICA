package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.runners;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services.ComuneProvinciaServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CsvRunner implements ApplicationRunner {

    @Autowired
    private ComuneProvinciaServ comuneProvinciaServ;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Percorso per i file CSV
        String provinceFilePath = "path/to/province-italiane.csv";
        String comuniFilePath = "path/to/comuni-italiani.csv";

        // Importa province
        comuneProvinciaServ.importaProvince(provinceFilePath);

        // Importa comuni
        comuneProvinciaServ.importaComuni(comuniFilePath);
    }
}
