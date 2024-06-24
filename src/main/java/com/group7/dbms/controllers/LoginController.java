package com.group7.dbms;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;

import java.util.List;

import spark.Request;
import spark.Response;
import spark.Spark;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class LoginController {
    private PersonsDAO personsDAO;
    private EmployeesDAO employeesDAO;
    private CustomersDAO customersDAO;
    private Argon2 argon2 = Argon2Factory.create();
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
                String password = json.getString("password", "");
                //String passwordHash = "";
                Person person = personsDAO.getByEmail(email);
                if (person == null) {
                    return "Not registered";
                }
                if (argon2.verify(person.getPasswordHash(), password)) {
                //if (person.getPasswordHash().equals(passwordHash)) {
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
        //registering a customer
        Spark.post("/register", (req, res) -> {
            try {
                JsonObject json = Json.parse(req.body()).asObject();
                String email = json.getString("email", "");
                String password = json.getString("password", "");
                String name = json.getString("name", "");
                if (personsDAO.getByEmail(email) == null) {
                    Person person = new Person();
                    person.setEmail(email);
                    person.setName(name);
                    person.setPasswordHash(argon2.hash(2, 66536, 1, password));
                    Customer customer = new Customer();
                    customer.setPerson(person);
                    customer.setDeliveryAddress(CustomerDeserializer.extractDeliveryAddress(json));
                    customer = customersDAO.save(customer);
                    res.status(201);
                    return PersonView.dump(person);
                } else return "Email taken!";
                
            } catch (Exception e) {
                return e;
            }
        });

    }
}