package it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.services;

import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Cliente;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.entities.Indirizzo;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.db.repositories.ClienteRepository;
import it.epicode.BW5_CRM_AZIENDA_ENERGETICA.web.dto.ClienteRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    IndirizzoService indirizzoService;


    public Cliente save(ClienteRequest newCliente) {
        Cliente cliente = new Cliente();

        BeanUtils.copyProperties(newCliente, cliente);

        if (newCliente.getIndirizziIds() != null && !newCliente.getIndirizziIds().isEmpty()) {
            for (Long indirizzoId : newCliente.getIndirizziIds()) {
                Indirizzo indirizzo = indirizzoService.findById(indirizzoId);
                if (indirizzo != null) {
                    cliente.getIndirizzi().add(indirizzo);
                } else {
                    System.err.println("Indirizzo con ID " + indirizzoId + " non trovato.");
                }
            }
        }

        return clienteRepository.save(cliente);
    }

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Cliente findById(Long id){
        return clienteRepository.findById(id).get();
    }

    public Cliente update(Long id, ClienteRequest newCliente){
        Cliente c = clienteRepository.findById(id).get();
        BeanUtils.copyProperties(newCliente, c);
        if (newCliente.getIndirizziIds() != null && !newCliente.getIndirizziIds().isEmpty()) {
            for (Long indirizzoId : newCliente.getIndirizziIds()) {
                Indirizzo indirizzo = indirizzoService.findById(indirizzoId);
                if (indirizzo != null) {
                    c.getIndirizzi().add(indirizzo);
                } else {
                    System.err.println("Indirizzo con ID " + indirizzoId + " non trovato.");
                }
            }
        }
        return clienteRepository.save(c);
    }

    public Cliente delete (Long id){
        Cliente c = clienteRepository.findById(id).get();
        clienteRepository.delete(c);
        return c;
    }
}
