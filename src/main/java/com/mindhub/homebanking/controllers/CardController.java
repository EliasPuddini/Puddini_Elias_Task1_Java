package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private AccountRepository accountRepository;

    @RequestMapping("/cards")
    public List<CardDTO> getAll(){
        return cardRepository.findAll().stream()
                .map(card -> new CardDTO(card))
                .collect(toList());
    }
    @RequestMapping("/cards/{id}")
    public CardDTO getById(@PathVariable Long id){
        return new CardDTO(cardRepository.findById(id).orElse(null));
    }


    @RequestMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {

        Client AuthClient = clientRepository.findByEmail(authentication.getName());


        if (cardRepository.findByClient(AuthClient).stream().anyMatch(card -> card.getType().equals(cardType) && card.getColor().equals(cardColor))){
            return new ResponseEntity<>("Already have a "+cardType+" card "+cardColor+".", HttpStatus.FORBIDDEN);
        }
        Card newCard = new Card(cardType, cardColor, LocalDate.now());

        do {
            newCard.setNumber(genRandomCardNumber());
        } while (cardRepository.findByNumber(newCard.getNumber()) != null);

        newCard.setCvv(genCvv(newCard.getNumber()));

        AuthClient.addCard(newCard);
        cardRepository.save(newCard);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
