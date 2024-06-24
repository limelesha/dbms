package com.group7.dbms;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.JsonArray;
import spark.Request;
import spark.Response;
import spark.Spark;

public class FeedbackController {
    private FeedbackDAO feedbackDAO;
    private PersonsDAO personsDAO;
    private ProductsDAO productsDAO;
    public FeedbackController(FeedbackDAO feedbackDAO, PersonsDAO personsDAO, ProductsDAO productsDAO) {
        this.feedbackDAO = feedbackDAO;
        this.personsDAO = personsDAO;
        this.productsDAO = productsDAO;
    }

    public void ignite() {
        //get by id
        Spark.get("/feedback/:feedback-id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":feedback-id"));
                Feedback feedback = feedbackDAO.getByID(id);
                if (feedback != null) {
                    res.type("application/json");
                    return FeedbackView.dump(feedback);
                } else {
                    res.status(404);
                    return "Feedback not found";
                }
            } catch (Exception e) {
                return e;
            }
        });

        // get all
        Spark.get("/feedback/", (req, res) -> {
            try {
                res.type("application/json");
                return FeedbackView.dump(feedbackDAO.getAllFeedback());
            } catch (Exception e) {
                return e;
            }
            
        });

        // add feedback
        Spark.post("/feedback/", (req, res) -> {
            try {
                JsonObject json = Json.parse(req.body()).asObject();
                Feedback feedback = new Feedback();
                Long productId = RecipeDeserializer.extractProductId(json);
                Long authorId = FeedbackDeserializer.extractAuthorId(json);
                feedback.setProduct(productsDAO.getByID(productId));
                feedback.setAuthor(personsDAO.getByID(authorId));
                feedback.setRating(FeedbackDeserializer.extractRating(json));
                feedback.setComment(FeedbackDeserializer.extractComment(json));
                feedback = feedbackDAO.save(feedback);
                res.status(201);
                return FeedbackView.dump(feedback); 
            } catch (Exception e) {
                return e;
            }
        });

        // edit feedback
        Spark.put("/feedback/:feedback-id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":feedback-id"));
                JsonObject json = Json.parse(req.body()).asObject();
                Feedback feedback = feedbackDAO.getByID(id);
                if (feedback != null) {
                    feedback.setRating(FeedbackDeserializer.extractRating(json));
                    feedback.setComment(FeedbackDeserializer.extractComment(json));
                    feedbackDAO.update(feedback);
                    return FeedbackView.dump(feedback);
                } else {
                    res.status(404);
                    return "Feedback not found";
                }
            } catch (Exception e) {
                return e;
            }
        });

        // delete feedback 
        Spark.delete("/feedback/:feedback-id", (req, res) -> {
            Long id = Long.parseLong(req.params(":feedback-id"));
            Feedback feedback = feedbackDAO.getByID(id);
                if (feedback != null) {
                    feedbackDAO.remove(id);
                    return "Review deleted";
                } else {
                    res.status(404);
                    return "Feedback not found";
                }
        });
    }
}