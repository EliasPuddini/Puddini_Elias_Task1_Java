package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientLoanRecord;
import com.mindhub.homebanking.dtos.LoanDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface LoanService {
    List<LoanDTO> getAll();

    void createLoan(
            ClientLoanRecord clientLoanRecord,
            Authentication authentication);
}
