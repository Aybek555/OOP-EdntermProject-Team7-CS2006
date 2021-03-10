package com.endterm.controllers;

import com.endterm.entities.Customer;
import com.endterm.repositories.CustomerRepository;

import java.util.List;

public class CustomerController {
    private final CustomerRepository repo;

    public CustomerController(CustomerRepository repo){
        this.repo=repo;
    }

    public String createCustomer(){
        boolean created=repo.createCustomer();
        return (created ? "Customer was registered successfully!" : "Registration was failed");
    }

    public String getCustomerByID(int id){
        Customer user=repo.getCustomerByID(id);
        return (user !=null ? user.toString() : "User was not found!");
    }

    public String getCustomerByIIN(String iin){
        Customer user=repo.getCustomerByIIN(iin);
        return (user !=null ? user.toString() : "User was not found!");
    }

    public String getAllCustomers(){
        List<Customer> users=repo.getAllCustomers();
        return users.toString();
    }
}
