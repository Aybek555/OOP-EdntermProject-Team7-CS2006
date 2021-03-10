package com.endterm.repositories;

import com.endterm.data.interfaces.IDB;
import com.endterm.entities.Department;
import com.endterm.entities.Manager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
       Manager:
       4.Make holiday for employee (partially done)
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
            String sql = "INSERT INTO manager (fname,lname,age,iin,phone_number,address,salary,department_id) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            System.out.println("Enter manager name: ");
            String name = sc.next();while(name.matches(".*\\d.*")){
                System.out.println("Name cannot include digits");
                name = sc.next();
            }
            ps.setString(1, name);


            System.out.println("Enter manager surname: ");
            String surname = sc.next();while(surname.matches(".*\\d.*")){
                System.out.println("Surname cannot include digits");
                surname = sc.next();
            }
            ps.setString(2, surname);


            System.out.println("Enter manager age: ");
            int age = sc.nextInt();
            ps.setInt(3, age);

            System.out.println("Enter manager iin: ");
            String iin = sc.next();
            while (iin.length()!=12){
                System.out.println("Please enter correct IIN 12 characters");
                iin = sc.next();
            }

            ps.setString(4, iin);

            System.out.println("Enter manager phone: ");
            String phone = sc.next();
            while (phone.charAt(0) != '+' || phone.length()!=12){
                System.out.println("Please enter correct phone number(Example: +77002883587)");
                phone = sc.next();
            }
            ps.setString(5, phone);

            System.out.println("Enter manager address: ");
            sc.nextLine();
            String address = sc.nextLine();
            ps.setString(6, address);

            System.out.println("Enter manager salary: ");
            int salary=sc.nextInt();
            ps.setInt(7,salary);

            ps.setInt(8,dep.getId());
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
            String sql = "SELECT * FROM manager";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Manager> managers = new ArrayList<>();
            while (rs.next()) {
                Manager manager = new Manager("ID:" + rs.getInt("manager_id") + rs.getString("fname"), rs.getString("lname"),rs.getString("iin"), rs.getString("phone_number"), rs.getString("address"), rs.getInt("age"),rs.getInt("salary"));
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
    public boolean hireEmployee(int managerID){
        EmployeeRepository repo = new EmployeeRepository(db);
        System.out.println("Enter employee's occupation");
        String tempOccupation = sc.nextLine();
        return repo.createEmployee(tempOccupation,managerID);
    }

    public Boolean dismissEmployee(int managerID){
        try{
            Connection con = null;
            try {
                con = db.getConnection();
                String sql = "SELECT * FROM employee where manager_id= " + managerID;
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                System.out.println("Select employeeID who you want to dismiss:");
                while(rs.next()){
                    System.out.println("ID:" + rs.getInt("employee_id") + " " +  rs.getString("fname") +" "+ rs.getString("lname"));
                }
                int tempEmployeeID = sc.nextInt();
                sql = "delete from employee where employee_id = " + tempEmployeeID ;
                st.executeUpdate(sql);

            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
                return false;
            } finally {
                try {
                    con.close();
                } catch (Exception e) {
                    System.out.println("Some exception occurred: " + e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public Boolean makeHolidayForEmployee(int managerID){
        try{
            Connection con = null;
            try {
                con = db.getConnection();
                String sql = "SELECT * FROM employee where manager_id= " + managerID;
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                System.out.println("Select employeeID who you want to give holiday:");
                while(rs.next()){
                    System.out.println("ID:" + rs.getInt("employee_id") + " " +  rs.getString("fname") +" "+ rs.getString("lname"));
                }
                int tempEmployeeID = sc.nextInt();
                sql = "update employee set isonholiday = true where employee_id = " + tempEmployeeID ;
                st.executeUpdate(sql);
                return true;
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
                return false;
            } finally {
                try {
                    con.close();
                } catch (Exception e) {
                    System.out.println("Some exception occurred: " + e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public Boolean isOnHoliday(){
        try{
            Connection con = null;
            try {
                con = db.getConnection();
                String sql = "SELECT * FROM employee";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                System.out.println("Select employee id to check if employee is on holidays");
                while(rs.next()){
                    System.out.println("ID:" + rs.getInt("employee_id") + " " +  rs.getString("fname") +" "+ rs.getString("lname"));
                }
                int tempEmployeeID = sc.nextInt();
                sql = "SELECT isonholiday FROM employee where employee_id= " + tempEmployeeID;
                rs = st.executeQuery(sql);
                while (rs.next()){
                    return rs.getBoolean("isonholiday");
                }

            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
                return false;
            } finally {
                try {
                    con.close();
                } catch (Exception e) {
                    System.out.println("Some exception occurred: " + e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public Boolean changeSalary(int managerID){
        try{
            Connection con = null;
            try {
                con = db.getConnection();
                String sql = "SELECT * FROM employee where manager_id = " + managerID;
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                System.out.println("Select employee id to change salary");
                while(rs.next()){
                    System.out.println("ID:" + rs.getInt("employee_id") + " " +  rs.getString("fname") +" "+ rs.getString("lname") + " " + rs.getInt("salary"));
                }
                int tempEmployeeID = sc.nextInt();
                System.out.println("Enter new salary for this employee: ");
                int newSalary = sc.nextInt();
                sql = "update employee set salary = " + newSalary + " where employee_id = " + tempEmployeeID;
                st.executeUpdate(sql);
                return true;

            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
                return false;
            } finally {
                try {
                    con.close();
                } catch (Exception e) {
                    System.out.println("Some exception occurred: " + e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
