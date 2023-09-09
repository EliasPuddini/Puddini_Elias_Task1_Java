package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {
    List<ClientDTO> getClientsDTO();

    void saveClient(Client client);

    ClientDTO getClientsDTOById(Long id);

    ClientDTO getCurrentClient(Authentication authentication);
}
