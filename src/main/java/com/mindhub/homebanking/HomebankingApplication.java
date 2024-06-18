package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	public CommandLineRunner init(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return args -> {


			//client's creation

			Client client = new Client("Melba", "Morel", "epuddini@gmail.com", passwordEncoder.encode("1234"));
			Client client2 = new Client("admin","admin","lpuddini@gmail.com", passwordEncoder.encode("1346"));


			//account's creation

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


			//Transaction's creation

			Transaction transaction = new Transaction(TransactionType.DEBIT, 15000, "transfer of mom");
			Transaction transaction3 = new Transaction(TransactionType.DEBIT, -5000, "dinner");
			Transaction transaction2 = new Transaction(TransactionType.CREDIT, 10000, "transfer of dad");

			transaction.setAccount(account);
			transaction2.setAccount(account1);
			transaction3.setAccount(account);

			account.addTransaction(transaction);
			account1.addTransaction(transaction2);
			account.addTransaction(transaction3);


			//Loan's creation

			Loan loan = new Loan("mortgage loan",500000, List.of(12,24,36,48,60));
			Loan loan1 = new Loan("Personal loan",100000,List.of(6,12,24));
			Loan loan2 = new Loan("Car Loan",300000,List.of(6,12,24,36));


			ClientLoan clientLoan = new ClientLoan(400000,60,client,loan);
			ClientLoan clientLoan1 = new ClientLoan( 50000,12,client,loan1);

			ClientLoan clientLoan2 = new ClientLoan(100000,24,client2,loan1);
			ClientLoan clientLoan3 = new ClientLoan(200000,36,client2,loan2);

			client.addClientLoan(clientLoan);
			client.addClientLoan(clientLoan1);
			client2.addClientLoan(clientLoan2);
			client2.addClientLoan(clientLoan3);



			//Card's creation

			Card card = new Card(CardType.DEBIT,CardColor.GOLD,LocalDate.now());
			Card card1 = new Card(CardType.CREDIT,CardColor.TITANIUM,LocalDate.now());
			Card card2 = new Card(CardType.CREDIT,CardColor.SILVER,LocalDate.now());

			card.setCardHolder("melba Morel");
			card.setNumber("4321567812345674");
			card.setCvv(879);
			card.setThruDate(LocalDate.now().plusYears(5));

			card1.setCardHolder("melba Morel");
			card1.setNumber("4321567812345678");
			card1.setCvv(870);
			card1.setThruDate(LocalDate.now().plusYears(5));

			card2.setCardHolder("Lautaro Puddini");
			card2.setNumber("4321567812345600");
			card2.setCvv(881);
			card2.setThruDate(LocalDate.now().plusYears(5));

			card.setClient(client);
			card1.setClient(client);
			card2.setClient(client2);

			client.addCards(card);
			client.addCards(card1);
			client2.addCards(card2);


			//saves

			clientRepository.save(client);
			clientRepository.save(client2);

			accountRepository.save(account);
			accountRepository.save(account1);
			accountRepository.save(account2);

			transactionRepository.save(transaction);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);

			loanRepository.save(loan);
			loanRepository.save(loan1);
			loanRepository.save(loan2);

			clientLoanRepository.save(clientLoan);
			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);

			cardRepository.save(card);
			cardRepository.save(card1);
			cardRepository.save(card2);

		};
	}
}
