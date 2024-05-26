package com.group7.dbms;

import java.util.List;


public interface FeedbackDAO {
    public Feedback getByID(Long id);
    public List<Feedback> getByProductId(Long id);
    public List<Feedback> getByPersonId(Long id);
    public List<Feedback> getAllFeedback();
    public Feedback save(Feedback feedback);
    public void update(Feedback feedback);
    public void remove(Long id);
    public void remove(Feedback feedback);
}