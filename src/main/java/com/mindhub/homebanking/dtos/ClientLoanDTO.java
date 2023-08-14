package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class ClientLoanDTO {
    private long id;
    private long amount;
    private long payments;

    private Client client;
    private Loan loan;

    public ClientLoanDTO(ClientLoan clientLoan){
        id = clientLoan.getId();
        amount = clientLoan.getAmount();
        payments = clientLoan.getPayments();
        client = clientLoan.getClient();
        loan = clientLoan.getLoan();
    }

    public long getId() {
        return id;
    }


    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getPayments() {
        return payments;
    }

    public void setPayments(long payments) {
        this.payments = payments;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
