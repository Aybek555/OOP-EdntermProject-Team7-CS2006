package com.endterm.controllers;

import com.endterm.entities.Employee;
import com.endterm.repositories.EmployeeRepository;

import java.util.List;
import java.util.Scanner;

public class EmployeeController {
    Scanner sc = new Scanner(System.in);
    private final EmployeeRepository repo;
    public EmployeeController(EmployeeRepository repo){
        this.repo=repo;
    }

    public String createEmployee(String occupation,int manager_id){
        boolean created=repo.createEmployee(occupation,manager_id);
        return (created ? "Employee created successfully" : " Creation was failed");
    }

    public String getEmpByID(int id){
        Employee emp=repo.getEmployeeByID(id);
        return (emp!=null ? emp.toString() : "Employee was not found!");
    }
    public String getEmpByIIN(String iin){
        Employee emp=repo.getEmployeeByIIN(iin);
        return (emp!=null ? emp.toString() : "Employee was not found!");
    }
    public List<Employee> getAllEmp(){
        List<Employee> emps=repo.getAllEmployee();
        return emps;
    }
    public void service(){
        repo.service();
        System.out.println("Done!");
    }
    public String chooseVacancy(){
        System.out.println("Select vacancy for employee");
        System.out.println("1.");
        System.out.println("2.");
        System.out.println("3.");
        System.out.println("4.");
        System.out.println("5.");
        String tempChoice = sc.next();
        return tempChoice;
    }

    public int chooseManager(){
        return repo.chooseManager();
    }
}
