package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;

import java.time.LocalDate;

public class CardDTO {

    public long id;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private LocalDate fromDate;
    private LocalDate thruDate;
    private long cvv;

    public CardDTO(){

    }
    public CardDTO(Card card){

        id = card.getId();
        cardHolder = card.getCardHolder();
        type = card.getType();
        color = card.getColor();
        number = card.getNumber();
        fromDate = card.getFromDate();
        thruDate = card.getThruDate();
        cvv = card.getCvv();
    }

    public long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public long getCvv() {
        return cvv;
    }

}
