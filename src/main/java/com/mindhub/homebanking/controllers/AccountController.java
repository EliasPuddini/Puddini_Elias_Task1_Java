package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAll(){
        return accountRepository.findAll().stream()
                .map(account -> new AccountDTO(account))
                .collect(toList());
    }
    @RequestMapping("/accounts/{id}")
    public AccountDTO getById(@PathVariable Long id, Authentication authentication){
        if(!accountRepository.findById(id).get().getClient().getEmail().equals(authentication.getName())){
            return null;
        }
        return new AccountDTO(accountRepository.findById(id).orElse(null));
    }

    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.GET)

    public List<AccountDTO> getCurrentAccounts( Authentication authentication) {

        return accountRepository.findAll().stream()
                .filter(account -> account.getClient().getEmail().equals(authentication.getName()))
                .map(account -> new AccountDTO(account))
                .collect(toList());
    }

    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)

    public ResponseEntity<Object> createAccount(Authentication authentication) {

        if(authentication == null){
            return new ResponseEntity<>("Missing data. ", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(authentication.getName()).getAccounts().stream().count()==3) {

            return new ResponseEntity<>("Already have 3 accounts. ", HttpStatus.FORBIDDEN);

        }
        Account newAccount = new Account( ("VIN" + String.format("%03d",accountRepository.count()+1) ) , 0.0);
        Client AuthClient = clientRepository.findByEmail(authentication.getName());
        AuthClient.addAccount(newAccount);
        accountRepository.save(newAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
