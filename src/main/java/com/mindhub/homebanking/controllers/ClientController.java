package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientService clientService;

    @RequestMapping("/clients")
    public List<ClientDTO> getAll(){
        return clientService.getClientsDTO();
    }

    @RequestMapping(value = "/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientService.getClientsDTOById(id);
    }

    @RequestMapping(value = "/clients/current", method = RequestMethod.GET)
    public ClientDTO getCurrent ( Authentication authentication){
        return clientService.getCurrentClient(authentication);
    }


    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {

            if(firstName.isEmpty() && lastName.isEmpty() && email.isEmpty() && password.isEmpty()){
                return new ResponseEntity<>("Missing all data", HttpStatus.FORBIDDEN);
            }
            if(firstName.isBlank()){
                return new ResponseEntity<>("Missing first name data", HttpStatus.FORBIDDEN);
            }if(lastName.isBlank()){
                return new ResponseEntity<>("Missing last name data", HttpStatus.FORBIDDEN);
            }if(email.isBlank()){
                return new ResponseEntity<>("Missing email data", HttpStatus.FORBIDDEN);
            }if(password.isBlank()){
                return new ResponseEntity<>("Missing password data", HttpStatus.FORBIDDEN);
            }
        }

        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("email already in use", HttpStatus.FORBIDDEN);
        }
        if(!(firstName.isEmpty() && lastName.isEmpty() && email.isEmpty() && password.isEmpty())) {

            clientService.saveClient(new Client(firstName, lastName, email, passwordEncoder.encode(password)));
            return new ResponseEntity<>(HttpStatus.CREATED);

        }
        return new ResponseEntity<>("Error", HttpStatus.FORBIDDEN);
    }
}