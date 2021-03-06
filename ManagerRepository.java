package com.endterm.repositories;

import com.endterm.datа.interfaces.IDB;
import com.endterm.enteties.Department;
import com.endterm.enteties.Employee;
import com.endterm.enteties.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
       Manager:
       1.Hire employee
       2.Dismiss employee
       3.List all the employees
       4.Make holiday for employee
       5.Change employee's salary
       */
public class ManagerRepository {
    private final IDB db;
    private Scanner sc = new Scanner(System.in);

    public ManagerRepository(IDB db){
        this.db=db;
    }

    public boolean createManager(Department dep){
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "INSERT INTO manager (fname,lname,age,iin,phone_number,address,salarydepartment_id) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            System.out.println("Enter manager name: ");
            String name = sc.next();
            ps.setString(1, name);

            System.out.println("Enter manager surname: ");
            String surname = sc.next();
            ps.setString(2, surname);

            System.out.println("Enter manager age: ");
            int age = sc.nextInt();
            ps.setInt(3, age);

            System.out.println("Enter manager iin: ");
            String iin = sc.next();
            ps.setString(4, iin);

            System.out.println("Enter manager phone: ");
            String phone = sc.next();
            ps.setString(5, phone);

            System.out.println("Enter manager address: ");
            sc.nextLine();
            String address = sc.nextLine();
            ps.setString(6, address);

            System.out.println("Enter manager salary: ");
            int salary=sc.nextInt();
            ps.setInt(7,salary);

            ps.setInt(8,dep.getId());
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

    public Manager getManagerByID(int id){
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT * FROM manager where manager_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Manager manager = new Manager(rs.getInt("manager_id"), rs.getString("fname"), rs.getString("lname"),rs.getString("iin"), rs.getString("phone_number"), rs.getString("address"), rs.getInt("age"),rs.getInt("salary"));
                return manager;
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

    public Manager getManagerByIIN(String iin){
            Connection con = null;
            try {
                con = db.getConnection();
                String sql = "SELECT * FROM manager where iin=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, iin);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Manager manager = new Manager(rs.getString("fname"), rs.getString("lname"),rs.getString("iin"), rs.getString("phone_number"), rs.getString("address"), rs.getInt("age"),rs.getInt("salary"));
                    return manager;
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
    public List<Manager> getAllManagers(){
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT * FROM employee";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Manager> managers = new ArrayList<>();
            while (rs.next()) {
                Manager manager = new Manager(rs.getString("fname"), rs.getString("lname"),rs.getString("iin"), rs.getString("phone_number"), rs.getString("address"), rs.getInt("age"),rs.getInt("salary"));
                managers.add(manager);
            }
            return managers;
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
