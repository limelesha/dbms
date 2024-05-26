package com.group7.dbms;

import java.util.List;


public interface EmployeesDAO {
    public Employee getByID(Long id);
    public Employee getByBakeryId(Long id);
    public Employee getByRole(Role role);
    public List<Employee> getAllEmployees();
    public Employee save(Employee employee);
    public void update(Employee employee);
    public void remove(Long id);
    public void remove(Employee employee);
}