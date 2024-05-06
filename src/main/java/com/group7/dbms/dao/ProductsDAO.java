package com.group7.dbms;

import java.util.List;

// import com.group7.dbms.models.Product;


public interface ProductsDAO {
    public Product getByID(Long id);
    public List<Product> getAllProducts();
    void save(Product product);
    void update(Product product);
    void remove(Long id);
    void remove(Product id);
}
