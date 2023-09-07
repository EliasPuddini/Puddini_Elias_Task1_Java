package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.mindhub.homebanking.utils.utils.genCvv;
import static com.mindhub.homebanking.utils.utils.genRandomCardNumber;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardService cardService;
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/cards")
    public List<CardDTO> getAll(){
        return cardService.getAllCardsDTO();
    }
    @RequestMapping("/cards/{id}")
    public CardDTO getById(@PathVariable Long id){
        return cardService.getById(id);
    }


    @RequestMapping(value = "/clients/current/cards",method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {

        if(authentication == null || (cardType != CardType.CREDIT && cardType != CardType.DEBIT) || (cardColor != CardColor.GOLD && cardColor != CardColor.SILVER && cardColor != CardColor.TITANIUM)){
            return new ResponseEntity<>("Missing data" + cardType,HttpStatus.FORBIDDEN);
        }

        Client authClient = clientRepository.findByEmail(authentication.getName());

        long count = clientRepository.findByEmail(authentication.getName()).getCards().stream().filter(card -> card.getType().equals(cardType)).count();
        if(count == 3){
            return new ResponseEntity<>("already have 3 cards of " + cardType,HttpStatus.FORBIDDEN);
        }


        cardService.createCard(cardType, cardColor, authClient);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
