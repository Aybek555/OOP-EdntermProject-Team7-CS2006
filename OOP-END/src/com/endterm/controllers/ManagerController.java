package com.endterm.controllers;

import com.endterm.entities.Department;
import com.endterm.entities.Manager;
import com.endterm.repositories.ManagerRepository;

import java.util.List;

public class ManagerController {
    private final ManagerRepository repo;

    public ManagerController(ManagerRepository repo){
        this.repo=repo;
    }

    public String createManager(Department dep){
        boolean created= repo.createManager(dep);
        return (created ? "Manager was created successfully!" : "Creation was failed!");
    }

    public String getManagerByID(int id){
        Manager man= repo.getManagerByID(id);
        return (man!=null ? man.toString() : "Manager was not found!");
    }

    public String getManagerByIIN(String iin){
        Manager m= repo.getManagerByIIN(iin);
        return (m!=null ? m.toString() : "Manager was not found!");
    }

    public List<Manager> getAllManagers(){
        List<Manager> managers=repo.getAllManagers();
        return managers;
    }
    public String hireEmployee(int managerID){
        boolean isHired = repo.hireEmployee(managerID);
        return (isHired ? "Employee was hired successfully!" : "Employee hiring was failed");
    }
    public String dismissEmployee(int managerID){
        boolean isDismissed = repo.dismissEmployee(managerID);
        return (isDismissed ? "Employee was dismissed successfully!" : "Employee dismission was failed");
    }

    public String makeHoliday(int managerID){
        boolean isGiven = repo.makeHolidayForEmployee(managerID);
        return (isGiven ? "This employee now have holidays" : "Employee giving holiday was failed");
    }
    public String isOnHoliday(){
        boolean result = repo.isOnHoliday();
        return (result ? "This employee is on holidays" : "Employee is working");
    }

    public String changeSalary(int managerID){
        boolean result = repo.changeSalary(managerID);
        return (result ? "Salary was updated" : "Updating was failed");
    }
}
