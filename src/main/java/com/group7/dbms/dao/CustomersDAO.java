package com.group7.dbms;

import java.util.List;


public interface CustomersDAO {
    public Customer getByID(Long id);
    public Customer getByEmail(String email);
    public List<Customer> getAllCustomers();
    public Customer save(Customer customer);
    public void update(Customer customer);
    public void remove(Long id);
    public void remove(Customer customer);
    public boolean isCustomer(Person person);
}