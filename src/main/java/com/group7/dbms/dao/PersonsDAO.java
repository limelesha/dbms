package com.group7.dbms;

import java.util.List;

public interface PersonsDAO {
    public Person getByID(Long id);
    public Person getByEmail(String email);
    public List<Person> getAllPersons();
    public Person save(Person person);
    public void update(Person person);
    public void remove(Long id);
    public void remove(Person person);
}
