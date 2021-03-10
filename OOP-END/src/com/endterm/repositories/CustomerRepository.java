package com.endterm.repositories;

import com.endterm.entities.Customer;
import com.endterm.data.interfaces.IDB;
import com.endterm.repositories.interfaces.ICustomerInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Take cash
//create card
//do payment
//transaction


public class CustomerRepository implements ICustomerInterface {
    private final IDB db;
    private Scanner sc = new Scanner(System.in);

    public CustomerRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createCustomer() {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "INSERT INTO customer (fname,lname,age,iin,phone_number,address) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            System.out.println("Enter your name: ");
            String name = sc.next();
            while(name.matches(".*\\d.*")){
                System.out.println("Name cannot include digits");
                name = sc.next();
            }
            ps.setString(1, name);

            System.out.println("Enter your surname: ");
            String surname = sc.next();
            ps.setString(2, surname);
            while(surname.matches(".*\\d.*")){
                System.out.println("Surname cannot include digits");
                surname = sc.next();
            }

            System.out.println("Enter your age: ");
            int age = sc.nextInt();
            ps.setInt(3, age);

            System.out.println("Enter your iin: ");
            String iin = sc.next();while (iin.length()!=12){
                System.out.println("Please enter correct IIN 12 characters");
                iin = sc.next();
            }
            ps.setString(4, iin);

            System.out.println("Enter your phone: ");
            String phone = sc.next();
            while (phone.charAt(0) != '+' || phone.length()!=12){
                System.out.println("Please enter correct phone number(Example: +77002883587)");
                phone = sc.next();
            }
            ps.setString(5, phone);

            System.out.println("Enter your address: ");
            sc.nextLine();
            String address = sc.nextLine();
            ps.setString(6, address);

            ps.execute();
            return true;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Some exception occurred: " + e);
            }
        }
        return false;
    }


    @Override
    public Customer getCustomerByID(int id) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT * FROM customer where customer_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer user = new Customer(rs.getInt("customer_id"), rs.getString("fname"), rs.getString("lname"), rs.getString("iin"), rs.getString("phone_number"), rs.getString("address"), rs.getInt("age"));
                return user;
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Some exception occurred: " + e);
            }
        }
        return null;
    }

    @Override
    public Customer getCustomerByIIN(String iin) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT * FROM customer where iin=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, iin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer user = new Customer(rs.getInt("customer_id"), rs.getString("fname"), rs.getString("lname"), rs.getString("iin"), rs.getString("phone_number"), rs.getString("address"), rs.getInt("age"));
                return user;
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Some exception occurred: " + e);
            }
        }
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT * FROM customer";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Customer> users = new ArrayList<>();
            while (rs.next()) {
                Customer user = new Customer(rs.getInt("customer_id"), rs.getString("fname"), rs.getString("lname"), rs.getString("iin"), rs.getString("phone_number"), rs.getString("address"), rs.getInt("age"));
                users.add(user);
            }
            return users;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Some exception occurred: " + e);
            }
        }
        return null;
    }


}


