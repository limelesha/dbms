package com.group7.dbms;

import java.util.List;


public interface ProductsDAO {
    public Product getByID(Long id);
    public List<Product> getAllProducts();
    Long save(Product product);
    void update(Product product);
    void remove(Long id);
    void remove(Product product);
}
