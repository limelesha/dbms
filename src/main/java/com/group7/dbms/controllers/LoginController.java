package com.group7.dbms;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;

import java.util.List;

import spark.Request;
import spark.Response;
import spark.Spark;

public class LoginController {
    //private PersonsDAO personsDAO;
    private EmployeesDAO employeesDAO;
    private CustomersDAO customersDAO;
    public LoginController(EmployeesDAO employeesDAO, CustomersDAO customersDAO) {
        //this.personsDAO = personsDAO;
        this.employeesDAO = employeesDAO;
        this.customersDAO = customersDAO;
    }
    public void ignite() {
        Spark.post("/login", (req, res) -> {
            try {
                JsonObject json = Json.parse(req.body()).asObject();
                String email = json.getString("email", "");
                String passwordHash = json.getString("passwordHash", "");
                Employee employee = employeesDAO.getByEmail(email);
                if (employee != null && employee.getPerson().getPasswordHash().equals(passwordHash)) {
                    req.session().attribute("person", employee);
                    res.status(200);
                    return "Logged in as employee";
                }
                Customer customer = customersDAO.getByEmail(email);
                if (customer != null && customer.getPerson().getPasswordHash().equals(passwordHash)) {
                    req.session().attribute("person", customer);
                    res.status(200);
                    return "Logged in as customer";
                }
                res.status(401);
                return "Unauthorized";
            } catch (Exception e) {
                return e;
            }
        });

        Spark.post("/logout", (req, res) -> {
            try {
                req.session().removeAttribute("person");
                res.status(200);
                return "Logged out";
            } catch (Exception e) {
                return e;
            }
        });
    }
}