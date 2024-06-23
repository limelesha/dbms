package com.group7.dbms;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;


public class EmployeeView {

    public static String dump(Employee employee) {
        return full(employee).toString();
    }

    public static String dump(Iterable<Employee> staff) {
        JsonArray json = Json.array();
        for (Employee employee : staff)
            json.add(partial(employee));
        return json.toString();
    }

    private static JsonValue full(Employee employee) {
        return Json.object()
            .add("person", employee.getPerson().getId())
            .add("role", employee.getRole().toString())
            .add("location", employee.getLocation() != null ? employee.getLocation().getId() : 0);
    }

    private static JsonValue partial(Employee employee) {
        return Json.object()
            .add("person", employee.getPerson().getId())
            .add("role", employee.getRole().toString());
    }

}
