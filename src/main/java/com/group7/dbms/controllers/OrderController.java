package com.group7.dbms;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;

import java.util.List;
import java.util.Set;

import spark.Request;
import spark.Response;
import spark.Spark;

public class OrderController {
    private OrdersDAO ordersDAO;
    private OrderItemsDAO orderItemsDAO;

    public OrderController(OrdersDAO ordersDAO, OrderItemsDAO orderItemsDAO) {
        this.ordersDAO = ordersDAO;
        this.orderItemsDAO = orderItemsDAO;
    }

    public void ignite() {
        //get by id
        Spark.get("/orders/:order-id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":order-id"));
                Order order = ordersDAO.getByID(id);
                if (order != null) {
                    Set<OrderItem> items = order.getItems();
                    JsonArray arr = Json.array();
                    // for (OrderItem item : items) {
                    //     arr.add(new JsonObject().add("product", item.getProduct().getId())
                    //         .add("quantity", item.getQuantity()));
                    // }
                    JsonValue json = Json.object()
                                    .add("id", id)
                                    //.add("items", arr)
                                    .add("customer", order.getCustomer().getPerson().getId());
                    res.type("application/json");
                    return json;
                } else {
                    res.status(404);
                    return "Order not found";
                }
            } catch (Exception e) {
                return e;
            }
        });

        // get all
        Spark.get("/orders/", (req, res) -> {
            try {
                res.type("application/json");
                List<Order> orders = ordersDAO.getAllOrders();
                JsonArray json = Json.array();
                for (Order order : orders) {
                    JsonValue jv = Json.object()
                                .add("id", order.getId())
                                .add("status", order.getStatus().name());
                    json.add(jv);
                }
                return json;
            } catch (Exception e) {
                return e;
            }
        });
        Spark.redirect.get("/orders", "/orders/");

        // Spark.post("/orders/:customer-id", (req, res) -> {
        //     try {
        //         Long customerId = Long.parseLong(req.params(":customer-id"));
        //         JsonObject json = Json.parse(req.body()).asObject();
        //         Order order = new Order();
        //         JsonArray jitems = json.get("items").asArray();
        //         for (JsonValue jitem : jitems) {

        //         }
        //         order = ordersDAO.save(order);
        //         res.status(201);
        //         return "";
        //     } catch (Exception e) {
        //         return e;
        //     }
        // });
    }
}