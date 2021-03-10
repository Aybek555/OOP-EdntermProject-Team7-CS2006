package com.endterm.entities;

public class Employee extends Person {
    private String occupation;
    private int salary;
    private boolean isOnHoliday;
    public Employee(String fname,String lname,String occupation,int salary, String iin,String phone,String address,int age){
        super(fname,lname,iin,phone,address,age);
        this.occupation=occupation;
        this.salary=salary;
    }

    public Employee(int id, String fname, String lname,String occupation,int salary, String iin, String phone_number, String address, int age) {
        super(id,fname,lname,iin,phone_number,address,age);
        this.occupation=occupation;
        this.salary=salary;
    }

    public boolean isOnHoliday() {
        return isOnHoliday;
    }

    public void setOnHoliday(boolean onHoliday) {
        isOnHoliday = onHoliday;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
