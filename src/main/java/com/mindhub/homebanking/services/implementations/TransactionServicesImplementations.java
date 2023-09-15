package com.mindhub.homebanking.services.implementations;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class TransactionServicesImplementations implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<TransactionDTO> getAll() {
        return transactionRepository.findAll().stream()
                .map(transaction -> new TransactionDTO(transaction))
                .collect(toList());
    }

    @Override
    public TransactionDTO getById(Long id) {
        return new TransactionDTO(transactionRepository.findById(id).orElse(null));
    }

    @Override
    public void makeTransaction(Double amount, String description, String accountFromNumber, String toAccountNumber, Authentication authentication) {

        if (amount > 0 && !(description.isEmpty()) && !(accountFromNumber.isEmpty()) && !(toAccountNumber.isEmpty()) && authentication != null){

            //creamos la transaccion
            Transaction transactionDebit = new Transaction(TransactionType.DEBIT, amount, description);
            Transaction transactionCredit = new Transaction(TransactionType.CREDIT, amount, description);
            Account originAccount = accountRepository.findByNumber(accountFromNumber);
            Account destinyAccount = accountRepository.findByNumber(toAccountNumber);

            //actualizamos el balance de la cuenta origen
            originAccount.setBalance(originAccount.getBalance() - amount);
            originAccount.addTransaction(transactionDebit);

            //actualizamos el balance de la cuenta destino
            destinyAccount.setBalance(originAccount.getBalance() + amount);
            destinyAccount.addTransaction(transactionCredit);

            //guardamos la transaccion
            transactionRepository.save(transactionDebit);
            transactionRepository.save(transactionCredit);

            //guardamos los cambios en las cuentas
            accountRepository.save(originAccount);
            accountRepository.save(destinyAccount);
        }
    }
}
