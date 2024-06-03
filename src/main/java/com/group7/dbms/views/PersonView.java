package com.group7.dbms;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;


public class PersonView {

    public static String dump(Person person) {
        return full(person).toString();
    }

    public static String dump(Iterable<Person> persons) {
        JsonArray json = Json.array();
        for (Person person : persons)
            json.add(partial(person));
        return json.toString();
    }

    private static JsonValue full(Person person) {
        return Json.object()
            .add("id", person.getId())
            .add("name", person.getName())
            .add("email", person.getEmail());
    }

    private static JsonValue partial(Person person) {
        return Json.object()
            .add("id", person.getId())
            .add("name", person.getName());
    }

}
