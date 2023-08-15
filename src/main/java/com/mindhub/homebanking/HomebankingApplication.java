package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
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
	public CommandLineRunner init(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository){
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

			account1.creationDate = LocalDate.now().plusDays(1);
			accountRepository.save(account);
			accountRepository.save(account1);
			accountRepository.save(account2);


			Transaction transaction = new Transaction(TransactionType.DEBIT, 15000, "transfer of mom");
			Transaction transaction3 = new Transaction(TransactionType.DEBIT, -5000, "dinner");
			Transaction transaction2 = new Transaction(TransactionType.CREDIT, 10000, "transfer of dad");

			transaction.setAccount(account);
			transaction2.setAccount(account1);
			transaction3.setAccount(account);

			account.addTransaction(transaction);
			account1.addTransaction(transaction2);
			account.addTransaction(transaction3);



			transactionRepository.save(transaction);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);



			Loan loan = new Loan("mortgage loan",500000, List.of(12,24,36,48,60));
			Loan loan1 = new Loan("Personal loan",100000,List.of(6,12,24));
			Loan loan2 = new Loan("Car Loan",300000,List.of(6,12,24,36));


			loanRepository.save(loan);
			loanRepository.save(loan1);
			loanRepository.save(loan2);

			ClientLoan clientLoan = new ClientLoan(400000,60,client,loan);
			ClientLoan clientLoan1 = new ClientLoan( 50000,12,client,loan1);

			ClientLoan clientLoan2 = new ClientLoan(100000,24,client2,loan1);
			ClientLoan clientLoan3 = new ClientLoan(200000,36,client2,loan2);

			client.addClientLoan(clientLoan);
			client.addClientLoan(clientLoan1);
			client2.addClientLoan(clientLoan2);
			client2.addClientLoan(clientLoan3);

			clientLoanRepository.save(clientLoan);
			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);


		};
	}
}
