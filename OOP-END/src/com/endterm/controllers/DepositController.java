package com.endterm.controllers;

import com.endterm.entities.Customer;
import com.endterm.repositories.DepositRepository;

public class DepositController {
    private final DepositRepository repo;
    public DepositController( DepositRepository repo){
        this.repo=repo;
    }

    public String openDeposit(Customer user){
        boolean isOpen=repo.openDeposit(user);
        return (isOpen ? "Deposit opened successfully!" : "Deposit delayed");
    }

    public String getCustomersDeposits(Customer user){
        return repo.getCustomersDeposits(user);
    }

    public int getTotalSUMOfDeposit(int id){
        return repo.getTotalSUMofDeposit(id);
    }

    public String closeDeposit(Customer user){
        boolean isClose= repo.closeDeposit(user);
        return (isClose ? "Deposit closed successfully!" : "Closing was failed");
    }
}
