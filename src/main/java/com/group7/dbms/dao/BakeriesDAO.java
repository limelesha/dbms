package com.group7.dbms;

import java.util.List;


public interface BakeriesDAO {
    public Bakery getByID(Long id);
    public List<Bakery> getAllBakeries();
    public Bakery save(Bakery bakery);
    public void update(Bakery bakery);
    public void remove(Long id);
    public void remove(Bakery bakery);
}