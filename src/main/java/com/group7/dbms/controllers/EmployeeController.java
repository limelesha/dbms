package com.group7.dbms;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;
import spark.Request;
import spark.Response;
import spark.Spark;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class EmployeeController {
    private EmployeesDAO employeesDAO;
    private BakeriesDAO bakeriesDAO;
    private PersonsDAO personsDAO;
    private Argon2 argon2 = Argon2Factory.create();
    public EmployeeController(EmployeesDAO employeesDAO, BakeriesDAO bakeriesDAO, PersonsDAO personsDAO) {
        this.employeesDAO = employeesDAO;
        this.bakeriesDAO = bakeriesDAO;
        this.personsDAO = personsDAO;
    }

    public void ignite() {
        // get by id
        Spark.get("/employees/:employee-id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":employee-id"));
                Employee employee = employeesDAO.getByID(id);
                if (employee != null) {
                    res.type("application/json");
                    return EmployeeView.dump(employee);
                } else {
                    res.status(404);
                    return "Employee not found";
                }
            } catch (Exception e) {
                return e;
            }
        });
        
        // get all
        Spark.get("/employees/", (req, res) -> {
            res.type("application/json");
            return EmployeeView.dump(employeesDAO.getAllEmployees());
        });

        Spark.redirect.get("/employees", "/employees/");

        // register employee 
        Spark.post("/employees/register", (req, res) -> {
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
                    Employee employee = new Employee();
                    employee.setPerson(person);
                    employee.setRole(EmployeeDeserializer.extractRole(json));
                    Bakery location = bakeriesDAO.getByID(EmployeeDeserializer.extractLocationId(json));
                    employee.setLocation(location);
                    employee = employeesDAO.save(employee);
                    res.status(201);
                    return PersonView.dump(person);
                } else return "Email taken!";
                
            } catch (Exception e) {
                return e;
            }
        });

        // add employee ?
        Spark.post("/employees/", (req, res) -> {
            try {
                JsonObject json = Json.parse(req.body()).asObject();
                Employee employee = new Employee();
                employee.setPerson(CustomerDeserializer.extractPerson(json));
                employee.setRole(EmployeeDeserializer.extractRole(json));
                Bakery location = bakeriesDAO.getByID(EmployeeDeserializer.extractLocationId(json));
                employee.setLocation(location);
                employee = employeesDAO.save(employee);
                res.status(201);
                return EmployeeView.dump(employee); 
            } catch (Exception e) {
                return e;
            }
        });

        // update
        Spark.put("/employees/:employee-id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":employee-id"));
                JsonObject json = Json.parse(req.body()).asObject();
                Bakery location = bakeriesDAO.getByID(EmployeeDeserializer.extractLocationId(json));
                Employee oldEmployee = employeesDAO.getByID(id);
                oldEmployee.setLocation(location);
                oldEmployee.setRole(EmployeeDeserializer.extractRole(json));
                employeesDAO.update(oldEmployee);
                return EmployeeView.dump(oldEmployee);
            } catch (Exception e) {
                return e;
            }
        });

        // delete 
        Spark.delete("/employees/:employee-id", (req, res) -> {
            Long id = Long.parseLong(req.params(":employee-id"));
            employeesDAO.remove(id);
            return "Employee deleted";
        });
    }
}