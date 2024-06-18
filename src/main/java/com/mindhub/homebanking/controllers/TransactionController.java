package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/transactions",method = RequestMethod.GET)
    public List<TransactionDTO> getAll(){
        return transactionService.getAll();
    }
    @RequestMapping(value = "/transactions/{id}",method = RequestMethod.GET)
    public TransactionDTO getById(@PathVariable Long id){
        return transactionService.getById(id);
    }

    @Transactional
    @RequestMapping(value = "/transactions",method = RequestMethod.POST)
    public ResponseEntity makeTransaction(
            @RequestParam Double amount, @RequestParam String description ,
            @RequestParam(value = "fromAccountNumber") String accountFromNumber, @RequestParam String toAccountNumber,
            Authentication authentication){


        //verificamos que los parametros no esten vacios
        if (amount == null || description == null || accountFromNumber == null || toAccountNumber == null) {//luego verificamos cual es el faltante y damos la respuesta
            if(amount == null && description == null && accountFromNumber == null && toAccountNumber == null){
                return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
            }if(amount == null){
                return new ResponseEntity<>("Missing amount data", HttpStatus.FORBIDDEN);
            }if(description == null){
                return new ResponseEntity<>("Missing description data", HttpStatus.FORBIDDEN);
            }if(accountFromNumber == null){
                return new ResponseEntity<>("Missing origin account number data", HttpStatus.FORBIDDEN);
            }if(toAccountNumber == null){
                return new ResponseEntity<>("Missing destination account number data", HttpStatus.FORBIDDEN);
            }
        }
        //verificamos que el numero de cuenta de origen y destino no sean iguales
        if (accountFromNumber.equals(toAccountNumber)) {
            return new ResponseEntity<>("The origin and destination accounts are the same", HttpStatus.FORBIDDEN);
        }
        //verificamos que el numero de cuenta de origen exista
        if (accountRepository.findByNumber(accountFromNumber) == null) {
            return new ResponseEntity<>("The origin account does not exist", HttpStatus.FORBIDDEN);
        }
        //verificamos que el numero de cuenta de destino exista
        if (accountRepository.findByNumber(toAccountNumber) == null) {
            return new ResponseEntity<>("The destination account does not exist", HttpStatus.FORBIDDEN);
        }
        //verificamos que el numero de cuenta de origen pertenezca al cliente logueado
        if (!accountRepository.findByNumber(accountFromNumber).getClient().getEmail().equals(authentication.getName())) {
            return new ResponseEntity<>("The origin account does not belong to the logged in client", HttpStatus.FORBIDDEN);
        }
        //vericiamos que hayta fondos en la cuenta origen
        if (accountRepository.findByNumber(accountFromNumber).getBalance() < amount) {
            return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);
        }
        //verificamos que el monto sea mayor a 0
        if (amount <= 0) {
            return new ResponseEntity<>("The amount must be greater than 0", HttpStatus.FORBIDDEN);
        }

        transactionService.makeTransaction(amount,description,accountFromNumber,toAccountNumber,authentication);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }


}
