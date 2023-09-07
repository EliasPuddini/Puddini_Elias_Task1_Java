package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientLoanRecord;
import com.mindhub.homebanking.dtos.LoanDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public abstract class LoanService {
    abstract List<LoanDTO> getAll();

    abstract void createLoan(
            ClientLoanRecord clientLoanRecord,
            Authentication authentication);
}
