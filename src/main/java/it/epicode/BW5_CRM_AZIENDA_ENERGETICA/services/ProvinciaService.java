package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.services;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.entities.Provincia;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

@Service
public class ProvinciaService {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    public void importaProvince(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            String[] data = line.split(";");
            if (data.length == 3) {
                String sigla = data[0];
                String nome = data[1];
                String regione = data[2];

                Provincia provincia = new Provincia();
                provincia.setSigla(sigla);
                provincia.setNome(nome);
                provincia.setRegione(regione);

                provinciaRepository.save(provincia);
            }
        }
    }

    public Collection<Provincia> findAll() {
        return provinciaRepository.findAll();
    }
}