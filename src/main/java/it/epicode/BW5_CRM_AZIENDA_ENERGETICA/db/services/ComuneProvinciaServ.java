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
                if (line.startsWith("Codice")) continue; // Salta l'intestazione
                String[] fields = line.split(";");
                if (fields.length < 2) {
                    System.err.println("Riga malformata: " + line);
                    continue;
                }
                Provincia provincia = new Provincia();
                provincia.setNome(fields[0].trim()); // Nome della provincia
                provincia.setSigla(fields[1].trim()); // Sigla della provincia
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
                if (line.startsWith("Codice")) continue; // Salta l'intestazione
                String[] fields = line.split(";"); // Usa il separatore corretto
                if (fields.length < 4) { // Controlla che ci siano almeno 4 campi
                    System.err.println("Riga malformata: " + line);
                    continue;
                }

                Comune comune = new Comune();
                comune.setNome(fields[2].trim()); // Nome del comune

                // Cerca la provincia corrispondente
                Provincia provincia = provinciaRepository.findByNome(fields[3].trim());
                if (provincia != null) {
                    comune.setProvincia(provincia);
                } else {
                    System.err.println("Provincia non trovata per: " + fields[3].trim());
                }

                comuneRepository.save(comune);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}