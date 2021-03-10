package com.endterm.repositories.interfaces;

import com.endterm.entities.Customer;

import java.util.List;

public interface ICustomerInterface {
    /**
     *
     * @return true if customer created successfully
     */
    boolean createCustomer();

    /**
     *
     * @param id-customer id
     * @return customer by his/her id
     */
    Customer getCustomerByID(int id);

    /**
     *
     * @param iin-customer iin
     * @return customer by his/her iin
     */
    Customer getCustomerByIIN(String iin);

    /**
     *
     *
     * @return list of all customers
     */
    List<Customer> getAllCustomers();
}
