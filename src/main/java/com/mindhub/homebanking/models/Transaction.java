package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;


    public Transaction(){

    }
    public Transaction(TransactionType type, double amount, String description){
        this.type = type;
        setAmount(amount);
        this.description = description;
        this.date = LocalDateTime.now();
    }

    public Account getAccount(){
        return account;
    }
    public void setAccount(Account account){
        this.account = account;
    }

    public long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setAmount(double amount) {
        if(this.type.equals(TransactionType.CREDIT)){
            this.amount = -amount;
        }else{
            this.amount = amount;
        }
    }
}
