package com.group7.dbms;

import java.util.List;


public interface OrderItemsDAO {
    public OrderItem getByID(Long id);
    public List<OrderItem> getByOrderId(Long id);
    public List<OrderItem> getByProductId(Long id);
    public List<OrderItem> getAllOrderItems();
    public OrderItem save(OrderItem orderItem);
    public void update(OrderItem orderItem);
    public void remove(Long id);
    public void remove(OrderItem orderItem);
}