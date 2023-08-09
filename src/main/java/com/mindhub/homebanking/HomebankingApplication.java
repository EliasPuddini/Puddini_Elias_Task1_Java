package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner init(ClientRepository clientRepository, AccountRepository accountRepository){
		return args -> {
			
			Client client = new Client("Elias", "Puddini", "epuddini@gmail.com");
			clientRepository.save(client);
			Client client2 = new Client("Lautaro","Puddini","lpuddini@gmail.com");
			clientRepository.save(client2);
			Account account = new Account(12,5000);
			Account account1 = new Account(10,7500);

			account.setClient(client);
			account1.setClient(client2);
			client.addAccount(account);
			client2.addAccount(account1);

			account1.creationDate = LocalDate.now().plusDays(1);
			accountRepository.save(account);
			accountRepository.save(account1);

		};
	}
}
