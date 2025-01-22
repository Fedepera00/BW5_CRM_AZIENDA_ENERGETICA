package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Cliente;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.SocietaLogo;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.repositories.ClienteRepository;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.repositories.SocietaLogoRepository;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.exceptions.AlreadyExistsException;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.dto.UploadLogoRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocietaLogoService {

    @Autowired
    private SocietaLogoRepository societaLogoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public SocietaLogo save(UploadLogoRequest newSocietaLogo, String imageUrl) {
        Cliente cliente = clienteRepository.findById(newSocietaLogo.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente non trovato con ID: " + newSocietaLogo.getClienteId()));

        SocietaLogo existingSocietaLogo = societaLogoRepository.findByClienteId(newSocietaLogo.getClienteId());

        if (existingSocietaLogo != null) {
            throw new AlreadyExistsException("Il Cliente con ID " + newSocietaLogo.getClienteId() + " ha già un avatar associato.");
        }

        SocietaLogo societaLogo = new SocietaLogo();
        societaLogo.setCliente(cliente);
        societaLogo.setImageUrl(imageUrl);

        // Aggiorna il campo logoAziendale del Cliente
        cliente.setLogoAziendale(imageUrl);
        clienteRepository.save(cliente); // Salva il cliente aggiornato

        return societaLogoRepository.save(societaLogo);
    }

}