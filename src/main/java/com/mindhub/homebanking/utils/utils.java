package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.repositories.AccountRepository;

import java.util.Random;

public class utils {
    public static String genRandomCardNumber() {
        String cardNumber = "8545";
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            cardNumber += "-" + String.format("%04d", random.nextInt(10000));
        }


        return cardNumber;

    }
    public static Integer genCvv(String cardNumber) {
        int sum = 0;
        for (String bloque : cardNumber.split("-")) {
            int aux = 0;
            for (int i = 0; i < 3; i++) {
                aux += Character.getNumericValue(bloque.charAt(i));
            }
            sum += aux;
        }
        return (300+(sum % 10000));
    }

    public static String genAccountId(AccountRepository accountRepository) {
        return "VIN"+String.format("%03d",accountRepository.count()+1 );
    }

}
