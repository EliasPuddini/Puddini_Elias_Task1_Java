package com.mindhub.homebanking.services;


import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;

public class CardService {
    List<CardDTO> getAllCardsDTO();

    CardDTO getById(Long id);

    void createCard(CardType cardType, CardColor cardColor, Client AuthClient);
}
