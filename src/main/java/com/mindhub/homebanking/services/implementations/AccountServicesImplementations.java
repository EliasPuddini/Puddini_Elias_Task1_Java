package com.mindhub.homebanking.services.implementations;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class AccountServicesImplementations implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream()
                .map(account -> new AccountDTO(account))
                .collect(toList());
    }

    @Override
    public AccountDTO getById(Long id, Authentication authentication) {
        return new AccountDTO(accountRepository.findById(id).orElse(null));
    }

    @Override
    public List<AccountDTO> getCurrentAccounts(Authentication authentication) {
        return accountRepository.findAll().stream()
                .filter(account -> account.getClient().getEmail().equals(authentication.getName()))
                .map(account -> new AccountDTO(account))
                .collect(toList());
    }

    @Override
    public void createAccount(Authentication authentication) {
        Account newAccount= new Account("VIN"+String.format("%03d",accountRepository.count()+1) , 0.0);
        Client AuthClient = clientRepository.findByEmail(authentication.getName());
        AuthClient.addAccount(newAccount);
        accountRepository.save(newAccount);
    }
}
