package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class ClientLoanDTO {
    private long id;
    private  String name;
    private double amount;
    private double payments;

    private Long loanId;

    public ClientLoanDTO(ClientLoan clientLoan){
        id = clientLoan.getId();
        this.name = clientLoan.getLoan().getName();
        amount = clientLoan.getAmount();
        payments = clientLoan.getPayments();
        loanId = clientLoan.getLoan().getId();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public double getPayments() {
        return payments;
    }

    public long getLoan() {
        return loanId;
    }
}
