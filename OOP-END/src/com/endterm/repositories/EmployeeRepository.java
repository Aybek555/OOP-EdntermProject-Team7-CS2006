package com.endterm.repositories;

/*
Employee:
1.Service
*/

import com.endterm.data.interfaces.IDB;
import com.endterm.entities.Employee;
import com.endterm.repositories.interfaces.IEmployeeInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeRepository implements IEmployeeInterface {
    private final IDB db;
    private Scanner sc=new Scanner(System.in);
    public ManagerRepository managerRepository;

    public EmployeeRepository(IDB db){
        this.db=db;
        this.managerRepository = new ManagerRepository(db);
    }

    @Override
    public boolean createEmployee(String occupation,int manager_id){
        Connection con=null;
        try{
            con=db.getConnection();
            String sql="INSERT INTO employee(fname,lname,age,occupation,salary,manager_id,phone_number,iin,address,isonholiday) values(?,?,?,?,?,?,?,?,?,? )";
            PreparedStatement ps=con.prepareStatement(sql);

            System.out.println("Enter employee name: ");
            String name = sc.next();
            while(name.matches(".*\\d.*")){
                System.out.println("Surname cannot include digits");
                name = sc.next();
            }
            ps.setString(1, name);


            System.out.println("Enter employee surname: ");
            String surname = sc.next();
            while(surname.matches(".*\\d.*")){
                System.out.println("Surname cannot include digits");
                surname = sc.next();
            }
            ps.setString(2, surname);


            System.out.println("Enter employee age: ");
            int age = sc.nextInt();
            ps.setInt(3, age);

            ps.setString(4,occupation);

            System.out.println("Enter employee's salary: ");
            int salary=sc.nextInt();
            ps.setInt(5,salary);

            ps.setInt(6,manager_id);

            System.out.println("Enter employee phone: ");
            String phone = sc.next();
            while (phone.charAt(0) != '+' || phone.length()!=12){
                System.out.println("Please enter correct phone number(Example: +77002883587)");
                phone = sc.next();
            }
            ps.setString(7, phone);

            System.out.println("Enter employee iin: ");
            String iin = sc.next();
            while (iin.length()!=12){
                System.out.println("Please enter correct IIN 12 characters");
                iin = sc.next();
            }
            ps.setString(8, iin);

            System.out.println("Enter employee address: ");
            sc.nextLine();
            String address = sc.nextLine();
            ps.setString(9,address);

            ps.setBoolean(10,false);

            ps.execute();
            return true;
        }catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public Employee getEmployeeByID(int id) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT * FROM employee where employee_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee emp = new Employee(rs.getInt("employee_id"), rs.getString("fname"), rs.getString("lname"),rs.getString("occupation"),rs.getInt("salary"),rs.getString("iin"), rs.getString("phone_number"), rs.getString("address"), rs.getInt("age"));
                return emp;
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
    public Employee getEmployeeByIIN(String iin) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT * FROM employee where iin=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, iin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee emp = new Employee(rs.getString("fname"), rs.getString("lname"),rs.getString("occupation"),rs.getInt("salary"),rs.getString("iin"), rs.getString("phone_number"), rs.getString("address"), rs.getInt("age"));
                return emp;
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
    public List<Employee> getAllEmployee() {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT * FROM employee";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Employee> emps = new ArrayList<>();
            while (rs.next()) {
                Employee emp = new Employee(rs.getInt("employee_id"), rs.getString("fname"), rs.getString("lname"),rs.getString("occupation"),rs.getInt("salary"),rs.getString("iin"), rs.getString("phone_number"), rs.getString("address"), rs.getInt("age"));
                emps.add(emp);
            }
            return emps;
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
    public void service() {
        Connection con = null;
        try {
            System.out.println("List of possible employee's");
            System.out.println(getAllEmployee());
            int tempEmployee = sc.nextInt();
            con = db.getConnection();
            String sql = "SELECT customer.customer_id, customer.fname, customer.lname FROM service" + " " +
                    "inner join customer on customer.customer_id = service.customer_id  where employee_id= " + tempEmployee;
            //PreparedStatement pt = con.prepareStatement(sql);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                System.out.println("ID:" + rs.getInt("customer_id") + " " + rs.getString("fname") + " " + rs.getString("lname"));
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
    }
    public int chooseManager() {
        Connection con = null;
        int tempEmployee;
        try {
            System.out.println("List of all managers");
            System.out.println(managerRepository.getAllManagers());
             tempEmployee= sc.nextInt();
            return tempEmployee;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }}
