package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Comune;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Provincia;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.repositories.ComuneRepository;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.repositories.ProvinciaRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class ComuneProvinciaServ {

    private final ComuneRepository comuneRepository;
    private final ProvinciaRepository provinciaRepository;

    public ComuneProvinciaServ(ComuneRepository comuneRepository, ProvinciaRepository provinciaRepository) {
        this.comuneRepository = comuneRepository;
        this.provinciaRepository = provinciaRepository;
    }

    // Metodo per importare le province
    public void importaProvince(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(","); // Supponendo che il CSV usi la virgola come separatore
                Provincia provincia = new Provincia();
                provincia.setNome(fields[0]); // Nome della provincia
                provincia.setSigla(fields[1]); // Sigla della provincia
                provinciaRepository.save(provincia);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo per importare i comuni
    public void importaComuni(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(","); // Supponendo che il CSV usi la virgola come separatore
                Comune comune = new Comune();
                comune.setNome(fields[0]); // Nome del comune

                // Cerca la provincia corrispondente nel database
                Provincia provincia = provinciaRepository.findByNome(fields[1]);
                if (provincia != null) {
                    comune.setProvincia(provincia);
                }

                comuneRepository.save(comune);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
