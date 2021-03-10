package com.endterm.repositories.interfaces;

import com.endterm.entities.Manager;

import java.util.List;

public interface IManagerInterface {
    /**
     *
     * @return true if manager was created successfully
     */
    boolean createManager();

    /**
     *
     * @param id-manager id
     * @return manager by his/her id
     */
    Manager getManagerByID(int id);

    /**
     *
     * @param iin-manager iin
     * @return manager by his/her iin
     */
    Manager getManagerByIIN(String iin);

    /**
     *
     * @return list of all managers
     */
    List<Manager> getAllManagers();

    Boolean hireEmployee(int managerID);

    Boolean dismissEmployee(int managerID);

    Boolean makeHolidayForEmployee(int managerID);

    Boolean isOnHoliday();

    Boolean changeSalary(int managerID);

}
