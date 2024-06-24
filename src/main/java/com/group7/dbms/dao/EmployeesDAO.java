package com.group7.dbms;

import java.util.List;


public interface EmployeesDAO {
    public Employee getByID(Long id);
    public Employee getByEmail(String email);
    public List<Employee> getByBakeryId(Long id);
    public List<Employee> getByRole(Role role);
    public List<Employee> getAllEmployees();
    public Employee save(Employee employee);
    public void update(Employee employee);
    public void remove(Long id);
    public void remove(Employee employee);
}