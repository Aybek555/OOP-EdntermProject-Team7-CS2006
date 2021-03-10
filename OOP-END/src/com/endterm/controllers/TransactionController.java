package com.endterm.controllers;

import com.endterm.entities.Card;
import com.endterm.repositories.TransactionRepository;

public class TransactionController {
    private final TransactionRepository transactionRepository;
    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository=transactionRepository;
    }
    public String transferKZT(Card c1, Card c2){
        boolean transfer=transactionRepository.transferKZT(c1,c2);
        return (transfer ? "Transferred successfully" : "Transferring was failed");
    }
}
