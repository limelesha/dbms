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
    private PersonsDAO personsDAO;
    private EmployeesDAO employeesDAO;
    private CustomersDAO customersDAO;
    public LoginController(PersonsDAO personsDAO, EmployeesDAO employeesDAO, CustomersDAO customersDAO) {
        this.personsDAO = personsDAO;
        this.employeesDAO = employeesDAO;
        this.customersDAO = customersDAO;
    }
    public void ignite() {
        Spark.post("/login", (req, res) -> {
            try {
                JsonObject json = Json.parse(req.body()).asObject();
                String email = json.getString("email", "");
                String passwordHash = json.getString("passwordHash", "");
                Person person = personsDAO.getByEmail(email);
                if (person == null) {
                    return "Not registered";
                }
                if (person.getPasswordHash().equals(passwordHash)) {
                    req.session(true);
                    req.session().attribute("person", person);
                    res.status(200);
                    return "Logged in";
                } else {
                    res.status(401);
                    return "Unauthorized";
                }
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