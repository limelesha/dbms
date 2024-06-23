package com.group7.dbms;

import java.util.List;

public interface OrdersDAO {
    public Order getByID(Long id);
    public List<Order> getAllOrders();
    public Order save(Order order);
    public void update(Order order);
    public void remove(Long id);
    public void remove(Order order);
}
