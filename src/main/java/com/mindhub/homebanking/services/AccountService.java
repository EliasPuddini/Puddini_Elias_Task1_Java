package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public class AccountService {
    List<AccountDTO> getAll();

    AccountDTO getById(Long id, Authentication authentication);

    List<AccountDTO> getCurrentAccounts( Authentication authentication);


    void createAccount(Authentication authentication);
}
