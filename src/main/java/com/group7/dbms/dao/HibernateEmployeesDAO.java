package com.group7.dbms;

import java.util.List;

import org.hibernate.SessionFactory;

public class HibernateEmployeesDAO implements EmployeesDAO {
    private SessionFactory sessionFactory;

    HibernateEmployeesDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    /**
     * Object by id value or null if not found
     */
    @Override
    public Employee getByID(Long id) {
        List<Employee> employees = sessionFactory.fromTransaction(session -> {
            return session.createQuery(
                "FROM Employee WHERE person.id = " + id, Employee.class
            ).list();
        });
        return employees.get(0);
    }

    @Override
    public List<Employee> getByBakeryId(Long id) {
        return sessionFactory.fromTransaction(session -> {
            return session.createQuery(
                "FROM Employee WHERE location.id = " + id, Employee.class
            ).list();
        });
    }

    @Override
    public List<Employee> getByRole(Role role) {
        return sessionFactory.fromTransaction(session -> {
            return session.createQuery(
                "FROM Employee WHERE role = " + role, Employee.class
            ).list();
        });
    }

    @Override
    public List<Employee> getAllEmployees() {
        return sessionFactory.fromTransaction(session -> {
            return session.createQuery(
                "FROM Employee", Employee.class
            ).list();
        });
    }

    @Override
    public Employee save(Employee employee) {
        return sessionFactory.fromTransaction(session -> {
            session.persist(employee);
            session.flush();
            return employee;
        });
    }

    @Override
    public void update(Employee employee) {
        sessionFactory.inTransaction(session -> {
            session.merge(employee);
        });
    }

    @Override
    public void remove(Employee employee) {
        sessionFactory.inTransaction(session -> {
            session.remove(employee);
        });
    }

    /**
     * Remove by identifier
     */
    @Override
    public void remove(Long id) {
        remove(getByID(id));
    }
}