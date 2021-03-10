package com.endterm.repositories.interfaces;

import com.endterm.entities.Employee;

import java.util.List;

public interface IEmployeeInterface {
    /**
     *
     * @param occupation-role of employee
     * @param manager_id-id of employee's manager
     * @return true if employee created successfully
     */
    boolean createEmployee(String occupation,int manager_id);

    /**
     *
     * @param id - id of employee
     * @return employee by his/her id
     */
    Employee getEmployeeByID(int id);


    /**
     *
     * @param iin-iin of employee
     * @return employee by his/her iin
     */
    Employee getEmployeeByIIN(String iin);

    /**
     *
     * @return list of all employee
     */
    List<Employee> getAllEmployee();
}
