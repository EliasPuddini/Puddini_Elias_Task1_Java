package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    public long id;
    public String number;
    public LocalDate date;
    public double balance;
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "account")
    private Set<Transaction> transaction = new HashSet<>();



    public Account(){

    }
    public Account(String number, double balance){
        this.number = number;
        this.date = LocalDate.now();
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    public Set<Transaction> getTransaction(){
        return transaction;
    }

    public void addTransaction(Transaction transaction){
        transaction.setAccount(this);
        this.transaction.add(transaction);
    }




    public long getId(){
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.date = creationDate;
    }

    public LocalDate getDate() {
        return date;
    }
}