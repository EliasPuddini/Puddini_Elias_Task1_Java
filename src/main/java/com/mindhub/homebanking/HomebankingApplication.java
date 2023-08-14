package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner init(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository){
		return args -> {
			
			Client client = new Client("Melba", "Morel", "epuddini@gmail.com");
			clientRepository.save(client);
			Client client2 = new Client("Lautaro","Puddini","lpuddini@gmail.com");
			clientRepository.save(client2);

			Account account = new Account("VIN001",5000);
			Account account1 = new Account("VIN002",7500);
			Account account2 = new Account("Vin003",8000);

			account.setClient(client);
			account2.setClient(client2);
			client.addAccount(account);
			client2.addAccount(account2);
			account1.setClient(client);
			client.addAccount(account1);

			account1.date = LocalDate.now().plusDays(1);
			accountRepository.save(account);
			accountRepository.save(account1);
			accountRepository.save(account2);


			Transaction transaction = new Transaction(TransactionType.DEBIT, 15000, "Pague algo");
			Transaction transaction3 = new Transaction(TransactionType.DEBIT, -5000, "Le envié dinero a mi hermano");
			Transaction transaction2 = new Transaction(TransactionType.CREDIT, 10000, "Pedi un prestamo");

			transaction.setAccount(account);
			transaction2.setAccount(account1);
			transaction3.setAccount(account);

			account.addTransaction(transaction);
			account1.addTransaction(transaction2);
			account.addTransaction(transaction3);



			transactionRepository.save(transaction);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);



			Loan loan = new Loan("Hipotecario",500000, List.of(12,24,36,48,60));
			Loan loan1 = new Loan("Personal",100000,List.of(6,12,24));
			Loan loan2 = new Loan("Automotriz",300000,List.of(6,12,24,36));


			loanRepository.save(loan);
			loanRepository.save(loan1);
			loanRepository.save(loan2);
		};
	}
}
