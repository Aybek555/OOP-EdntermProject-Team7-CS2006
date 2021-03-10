package com.endterm.controllers;

import com.endterm.entities.Customer;
import com.endterm.repositories.CardRepository;

public class CardController {
    private final CardRepository repo;
    public CardController(CardRepository repo){
        this.repo=repo;
    }

    public String createCard(Customer user) {
        boolean isCreated = repo.createCard(user);
        return (isCreated ? "Creation was finished successfully!" : "Creation was failed!");
    }

    public String getAllExistngCards(){
     return repo.getAllDBCards()+"";
    }

    public int getBalance(int id){
        return repo.getBalance(id);
    }



    public String getCardByID(int id){
        return repo.getCardByID(id)+"";
    }

    public  String getCustomerCards(Customer customer){
        return repo.getCustomerCards(customer)+"";
    }







}
