package com.group7.dbms;

import java.util.List;


public interface ProductsDAO {
    public Product getByID(Long id);
    public List<Product> getAllProducts();
    public Product save(Product product);
    public void update(Product product);
    public void remove(Long id);
    public void remove(Product product);
}
