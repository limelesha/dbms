package com.group7.dbms;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;


import spark.Request;
import spark.Response;
import spark.Spark;

public class BakeryController {
    private BakeriesDAO bakeriesDAO;

    public BakeryController(BakeriesDAO bakeriesDAO) {
        this.bakeriesDAO = bakeriesDAO;
    }

    public void ignite() {
        // get by id
        Spark.get("/bakeries/:bakery-id", (req, res) -> {
            Long id = Long.parseLong(req.params(":bakery-id"));
            Bakery bakery = bakeriesDAO.getByID(id);
            if (bakery != null) {
                res.type("application/json");
                return BakeryView.dump(bakery);
            } else {
                res.status(404);
                return "Bakery not found";
            }
        });
        
        // get all
        Spark.get("/bakeries/", (req, res) -> {
            res.type("application/json");
            return BakeryView.dump(bakeriesDAO.getAllBakeries());
        });

        Spark.redirect.get("/bakeries", "/bakeries/");

        // add bakery
        Spark.post("/bakeries/", (req, res) -> {
            JsonObject json = Json.parse(req.body()).asObject();
            Bakery bakery = BakeryDeserializer.deserialize(json);
            bakery = bakeriesDAO.save(bakery);
            res.status(201);
            return BakeryView.dump(bakery);
        });

        // update bakery
        Spark.put("/bakeries/:bakery-id", (req, res) -> {
            Long id = Long.parseLong(req.params(":bakery-id"));
            JsonObject json = Json.parse(req.body()).asObject();
            Bakery bakery = BakeryDeserializer.deserialize(json);
            Bakery oldBakery = bakeriesDAO.getByID(id);
            oldBakery.setAddress(bakery.getAddress());
            oldBakery.setOpenTime(bakery.getOpenTime());
            oldBakery.setCloseTime(bakery.getCloseTime());
            bakeriesDAO.update(oldBakery);
            return BakeryView.dump(oldBakery);
        });

        // delete bakery
        Spark.delete("/bakeries/:bakery-id", (req, res) -> {
            Long id = Long.parseLong(req.params(":bakery-id"));
            bakeriesDAO.remove(id);
            return "Bakery deleted";
        });
    }
}