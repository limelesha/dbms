package com.group7.dbms;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;


public class BakeryView {

    public static String dump(Bakery bakery) {
        return full(bakery).toString();
    }

    public static String dump(Iterable<Bakery> bakeries) {
        JsonArray json = Json.array();
        for (Bakery bakery : bakeries)
            json.add(partial(bakery));
        return json.toString();
    }

    private static JsonValue full(Bakery bakery) {
        return Json.object()
            .add("id", bakery.getId())
            .add("address", bakery.getAddress())
            .add("openTime", bakery.getOpenTime().toString())
            .add("closeTime", bakery.getCloseTime().toString());
    }

    private static JsonValue partial(Bakery bakery) {
        return Json.object()
            .add("id", bakery.getId())
            .add("address", bakery.getAddress());
    }

}
