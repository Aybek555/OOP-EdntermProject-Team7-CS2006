package com.endterm;

import com.endterm.controllers.CustomerController;
import com.endterm.controllers.EmployeeController;
import com.endterm.controllers.ManagerController;
import com.endterm.data.interfaces.IDB;
import com.endterm.entities.Department;
import com.endterm.repositories.CustomerRepository;
import com.endterm.repositories.EmployeeRepository;
import com.endterm.repositories.ManagerRepository;

import java.util.Scanner;

public class MyApplication {
    private final Scanner input;
    private final IDB db;

    public MyApplication(IDB db) {
        this.db = db;
        input = new Scanner(System.in);
    }
    public void start(){
        System.out.println("Application is started");
        System.out.println("Choose your role:");
        System.out.println("1.Customer");
        System.out.println("2.Employee");
        System.out.println("3.Manager");
        System.out.println("0.Exit program");
        int choice = input.nextInt();
        while (choice != 1 && choice != 2 && choice != 3 && choice != 0) {
            System.out.println("Choose only from possible options");
            choice = input.nextInt();
        }
        if (choice == 1) {
            MyApplicatioon customer=new MyApplicatioon();
            customer.Start();
        } else if (choice == 2) {
            EmployeeRepository employeeRepo = new EmployeeRepository(db);
            EmployeeController employeeController = new EmployeeController(employeeRepo);
            String tempOccupation = employeeController.chooseVacancy();
            int tempManagerID = employeeController.chooseManager();
            System.out.println(employeeController.createEmployee(tempOccupation, tempManagerID));
            while (true) {
                System.out.println("Choose method:");
                System.out.println("1. Get employee by ID");
                System.out.println("2. List all the employees");
                System.out.println("3. Get employee by IIN");
                System.out.println("0. Get employee by IIN");
                int employeeChoice = input.nextInt();
                while (employeeChoice != 1 && employeeChoice != 2 && employeeChoice != 3 && employeeChoice != 0) {
                    System.out.println("Choose only from possible options");
                    employeeChoice = input.nextInt();
                }
                if (employeeChoice == 1) {
                    System.out.println("Enter employeeID");
                    int tempEmployee = input.nextInt();
                    System.out.println(employeeController.getEmpByID(tempEmployee));
                } else if (employeeChoice == 2) {
                    System.out.println(employeeController.getAllEmp());
                } else if (employeeChoice == 3) {
                    System.out.println("Enter employee's IIN");
                    String tempIIN = input.next();
                    System.out.println(employeeController.getEmpByIIN(tempIIN));
                } else {
                    return;
                }

            }
        } else if (choice == 3) {
            ManagerRepository managerRepo = new ManagerRepository(db);
            ManagerController managerController = new ManagerController(managerRepo);
            Department dp = new Department();
            dp.setId(1);
            System.out.println(managerController.createManager(dp));
            while (true) {
                System.out.println("Choose method:");
                System.out.println("1. Get manager by ID");
                System.out.println("2. List all the managers");
                System.out.println("3. Get manager by IIN");
                System.out.println("4. Hire new employee");
                System.out.println("5. Dismiss employee");
                System.out.println("6. Give holidays for employee");
                System.out.println("7. Check if employee is on holiday");
                System.out.println("8. Change employee's salary");
                System.out.println("0. Exit programm");
                int managerChoice = input.nextInt();
                while (managerChoice != 1 && managerChoice != 2 &&  managerChoice != 3 && managerChoice != 4 && managerChoice != 5 && managerChoice != 6 && managerChoice != 7 && managerChoice != 8 && managerChoice != 0) {
                    System.out.println("Choose only from possible options");
                    managerChoice = input.nextInt();
                }
                if (managerChoice == 1) {
                    System.out.println("Enter manager's ID");
                    int tempManager = input.nextInt();
                    System.out.println(managerController.getManagerByID(tempManager));
                } else if (managerChoice == 2) {
                    System.out.println(managerController.getAllManagers());
                } else if (managerChoice == 3) {
                    System.out.println("Enter manager's IIN");
                    String tempIIN = input.next();
                    System.out.println(managerController.getManagerByIIN(tempIIN));
                } else if (managerChoice == 4){
                    System.out.println("Select manager ID to create employee which will be link with specific manager");
                    System.out.println(managerController.getAllManagers());
                    int tempID = input.nextInt();
                    System.out.println( managerController.hireEmployee(tempID));
                } else if (managerChoice == 5){
                    System.out.println("Select manager ID to see possible dismisses");
                    System.out.println(managerController.getAllManagers());
                    int tempID = input.nextInt();
                    System.out.println(managerController.dismissEmployee(tempID));
                } else if(managerChoice == 6){
                    System.out.println("Select manager ID to show all the possible employees to give holiday");
                    System.out.println(managerController.getAllManagers());
                    int tempID = input.nextInt();
                    System.out.println(managerController.makeHoliday(tempID));
                } else if(managerChoice == 7){
                    System.out.println(managerController.isOnHoliday());
                } else if(managerChoice == 8){
                    System.out.println("Select manager ID to see possible employees to change salary");
                    System.out.println(managerController.getAllManagers());
                    int tempID = input.nextInt();
                    System.out.println(managerController.changeSalary(tempID));
                }
                else {
                    return;
                }

            }
        } else {
            return;
        }
    }
}
