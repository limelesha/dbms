package com.group7.dbms;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;


public class CustomerView {

    public static String dump(Customer customer) {
        return full(customer).toString();
    }

    public static String dump(Iterable<Customer> customers) {
        JsonArray json = Json.array();
        for (Customer customer : customers)
            json.add(partial(customer));
        return json.toString();
    }

    private static JsonValue full(Customer customer) {
        return Json.object()
            .add("person", customer.getPerson().getId())
            .add("deliveryAddress", customer.getDeliveryAddress());
    }

    private static JsonValue partial(Customer customer) {
        return Json.object()
            .add("person", customer.getPerson().getId());
    }

}
