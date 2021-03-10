package com.endterm.controllers;

import com.endterm.entities.Customer;
import com.endterm.repositories.PaymentRepository;

public class PaymentController {
    private final PaymentRepository repo;

    public PaymentController(PaymentRepository repo){
        this.repo=repo;
    }


    public String createPayment(Customer user, String type){
        boolean isCreated=repo.createPayment(user,type);
        return (isCreated ? "Payment created successfully!":"Creation was failed");
    }

    public String getPayments(Customer user){
        return repo.getPayments(user);
    }

    public String doPayment(Customer user,String type){
        boolean pay= repo.doPayment(user,type);
        return (pay ? "Payment complete successfully" : "payment was delayed");
    }

    public int getPaymentSUM(int id,String type){
        return repo.getPaymentSumByID(id,type);
    }

    public String Pay(Customer user,String type){
        boolean isPayed= repo.doPayment(user,type);
        return (isPayed ? "Payment finished successfully!" : "Payment was failed");
    }

}
