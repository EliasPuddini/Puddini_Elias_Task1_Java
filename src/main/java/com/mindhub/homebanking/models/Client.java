package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.loader.collection.OneToManyJoinWalker;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "client")
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<ClientLoan> loans = new HashSet<>();

    @OneToMany(mappedBy = "client",fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();


    public Client() { }

    public Client(String first, String last, String email, String password) {
        this.firstName = first;
        this.lastName = last;
        this.email = email;
        this.password = password;
    }


    @JsonIgnore
    public List<Loan> getLoans (){
        return loans.stream().map(clientLoan -> clientLoan.getLoan()).collect(Collectors.toList());
    }

    public void addClientLoan (ClientLoan clientLoan){
        clientLoan.setClient(this);
        loans.add(clientLoan);
    }

    public void setLoans(Set<ClientLoan> loans) {
        this.loans = loans;
    }

    public Set<ClientLoan> getClientsLoans(){
        return loans;
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }
    public void addAccount(Account account){
        account.setClient(this);
        this.accounts.add(account);
    }

    public Long getId() {
        return id;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void addCards(Card card){
        this.cards.add(card);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}
