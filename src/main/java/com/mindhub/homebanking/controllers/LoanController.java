package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientLoanRecord;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private LoanService loanService;
    @RequestMapping(value="/loans",method = RequestMethod.GET)
    public List<LoanDTO> getAll(){
        return loanService.getAll();
    }

    @Transactional
    @RequestMapping(value="/loans",method = RequestMethod.POST)
    public ResponseEntity<Object> createLoan(
            @RequestBody ClientLoanRecord clientLoanRecord,
            Authentication authentication){

        Long loanId = clientLoanRecord.getLoanId();
        Double amount = clientLoanRecord.getAmount();
        Integer payments = clientLoanRecord.getPayments();
        String toAccountNumber = clientLoanRecord.getToAccountNumber();

        if (loanId==null || amount == null || payments == null || toAccountNumber.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (amount <= 0) {
            return new ResponseEntity<>("Amount must be positive", HttpStatus.FORBIDDEN);
        }
        if (payments <= 0) {
            return new ResponseEntity<>("Payments must be positive", HttpStatus.FORBIDDEN);
        }
        if (!loanRepository.findById(loanId).isPresent() ) {
            return new ResponseEntity<>("Loan not exist", HttpStatus.FORBIDDEN);
        }
        if (accountRepository.findByNumber(toAccountNumber) == null) {
            return new ResponseEntity<>("The destination account does not exist", HttpStatus.FORBIDDEN);
        }
        if (!accountRepository.findByNumber(toAccountNumber).getClient().getEmail().equals(authentication.getName())) {
            return new ResponseEntity<>("The destination account does not belong to the logged user", HttpStatus.FORBIDDEN);
        }

        if(amount>loanRepository.findById(loanId).get().getMaxAmount()){
            return new ResponseEntity<>("The amount is greater than the maximum allowed", HttpStatus.FORBIDDEN);
        }

        loanService.createLoan(clientLoanRecord,authentication);

        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

}
