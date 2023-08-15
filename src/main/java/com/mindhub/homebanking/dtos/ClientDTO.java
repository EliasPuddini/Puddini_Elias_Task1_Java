package com.mindhub.homebanking.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ClientDTO {
    private long id;
    private String firstName, lastName, email;
    private Set<AccountDTO> accounts;
    private Set<ClientLoanDTO> loans;

    public ClientDTO(Client client){
        id = client.getId();
        firstName = client.getFirstName();
        lastName = client.getLastName();
        email = client.getEmail();
        accounts = client.getAccounts().stream().map(element -> new AccountDTO(element)).collect(Collectors.toSet());
        loans = client.getClientsLoans().stream().map(element -> new ClientLoanDTO(element)).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }
}
