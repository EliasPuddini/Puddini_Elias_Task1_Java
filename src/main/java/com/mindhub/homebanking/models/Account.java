package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    public long id;
    public String number;
    public LocalDate creationDate;
    private LocalDateTime localDateTime;
    public double balance;
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "account")
    private Set<Transaction> transactions = new HashSet<>();




    public Account(){

    }
    public Account(String number, double balance){
        this.number = number;
        this.creationDate = LocalDate.now();
        this.balance = balance;
        this.localDateTime = LocalDateTime.now();
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction (Transaction transaction){
        transaction.setAccount(this);
        this.transactions.add(transaction);
    }

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Double getBalance() {
        return balance;
    }


    public void setClient(Client client) {
        this.client = client;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }


    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}