package com.endterm.entities;

  /*
        Customer:
        1.Registration
        2.Open deposit
        3.Do payments
        4.do transaction
        5.Take cash
        6.Register new wallet
     */

public class Customer extends Person {
    public Customer(String fname,String lname,String iin,String phone,String address,int age){
        super(fname,lname,iin,phone,address,age);
    }

    public Customer(int id, String fname, String lname, String iin, String phone_number, String address, int age) {
        super(id,fname,lname,iin,phone_number,address,age);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
