package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDate date;
    private double balance;

    private Set<TransactionDTO> transactions;

    public AccountDTO(Account account){
        id = account.getId();
        number = account.getNumber();
        date = account.getDate();
        balance = account.getBalance();
        transactions = account.getTransaction().stream().map(element -> new TransactionDTO(element)).collect(Collectors.toSet());
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getCreationDate() {
        return date;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.date = creationDate;
    }

    public double getBalance() {
        return balance;
    }

}