package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDate;

public class AccountDTO {
    public long id;
    public String number;
    public LocalDate date;
    public double balance;

    public AccountDTO(Account account){
        id = account.getId();
        number = account.getNumber();
        date = account.getDate();
        balance = account.getBalance();
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
