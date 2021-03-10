package com.endterm.entities;

public class Manager extends Person{
    private int salary;
    public Manager(String fname,String lname,String iin,String phone,String address,int age,int salary){
        super(fname,lname,iin,phone,address,age);
        this.salary=salary;
    }

    public Manager(int id, String fname, String lname, String iin, String phone_number, String address, int age,int salary) {
        super(id,fname,lname,iin,phone_number,address,age);
        this.salary=salary;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
