package com.group7.dbms;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;
import spark.Request;
import spark.Response;
import spark.Spark;

public class CustomerController {
    private CustomersDAO customersDAO;

    public CustomerController(CustomersDAO customersDAO) {
        this.customersDAO = customersDAO;
    }

    public void ignite() {
        // get by id
        Spark.get("/customers/:customer-id", (req, res) -> {
            Long id = Long.parseLong(req.params(":customer-id"));
            Customer customer = customersDAO.getByID(id);
            if (customer != null) {
                res.type("application/json");
                return CustomerView.dump(customer);
            } else {
                res.status(404);
                return "Customer not found";
            }
        });
        
        // get all
        Spark.get("/customers/", (req, res) -> {
            res.type("application/json");
            return CustomerView.dump(customersDAO.getAllCustomers());
        });

        Spark.redirect.get("/customers", "/customers/");

        // add customer 
        // TODO - change method to assign customer by person id probably
        // Spark.post("/customers/", (req, res) -> {
        //     try {
        //         JsonObject json = Json.parse(req.body()).asObject();
        //         Customer customer = new Customer();
        //         customer.setPerson(CustomerDeserializer.extractPerson(json));
        //         customer.setDeliveryAddress(CustomerDeserializer.extractDeliveryAddress(json));
        //         customer = customersDAO.save(customer);
        //         res.status(201);
        //         return CustomerView.dump(customer); 
        //     } catch (Exception e) {
        //         return e;
        //     }
        // });

        // update (can only update address)
        Spark.put("/customers/:customer-id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":customer-id"));
                JsonObject json = Json.parse(req.body()).asObject();
                String deliveryAddress = CustomerDeserializer.extractDeliveryAddress(json);
                Customer oldCustomer = customersDAO.getByID(id);
                oldCustomer.setDeliveryAddress(deliveryAddress);
                customersDAO.update(oldCustomer);
                return CustomerView.dump(oldCustomer);
            } catch (Exception e) {
                return e;
            }
        });

        // delete 
        Spark.delete("/customers/:customer-id", (req, res) -> {
            Long id = Long.parseLong(req.params(":customer-id"));
            customersDAO.remove(id);
            return "Customer deleted";
        });
    }
}