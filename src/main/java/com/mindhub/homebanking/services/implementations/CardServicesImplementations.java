package com.mindhub.homebanking.services.implementations;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.mindhub.homebanking.utils.utils.genCvv;
import static com.mindhub.homebanking.utils.utils.genRandomCardNumber;
import static java.util.stream.Collectors.toList;

@Service
public class CardServicesImplementations implements CardService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CardRepository cardRepository;


    @Override
    public List<CardDTO> getAllCardsDTO() {
        return cardRepository.findAll().stream()
                .map(card -> new CardDTO(card))
                .collect(toList());
    }

    @Override
    public CardDTO getById(Long id) {
        return new CardDTO(cardRepository.findById(id).orElse(null));
    }


    @Override
    public void createCard(CardType cardType, CardColor cardColor, Client AuthClient) {
        Card newCard = new Card(cardType, cardColor, LocalDate.now());

        do {
            newCard.setNumber(genRandomCardNumber());
        } while (cardRepository.findByNumber(newCard.getNumber()) != null);

        newCard.setCvv(genCvv(newCard.getNumber()));

        AuthClient.addCard(newCard);
        cardRepository.save(newCard);

    }
}
