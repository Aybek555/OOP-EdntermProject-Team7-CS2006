package com.endterm.entities;

public class Person {
private String fname;
private String lname;
private String iin;
private String phone_num;
private String address;
private int age;
private int id;

public Person(String fname,String lname,String iin,String phone,String address,int age){
    this.address=address;
    this.age=age;
    this.phone_num=phone;
    this.lname=lname;
    this.fname=fname;
    this.iin=iin;
}
    public Person(int id, String fname, String lname, String iin, String phone, String address, int age){
        this.address=address;
        this.age=age;
        this.phone_num=phone;
        this.lname=lname;
        this.fname=fname;
        this.iin=iin;
        this.id=id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getFname() {
        return fname;
    }

    public String getIin() {
        return iin;
    }

    public String getLname() {
        return lname;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return fname+" "+lname+" "+phone_num+" "+iin;
    }
}
