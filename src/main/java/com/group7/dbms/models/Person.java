package com.group7.dbms;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
public class Person {

    @RepresentationIncluded(RepresentationType.IDENTIFIER)
    @Id
    @GeneratedValue
    private Long id;

    @RepresentationIncluded(RepresentationType.PARTIAL)
    @Basic(optional=false)
    private String name;

    @RepresentationIncluded(RepresentationType.FULL)
    @Basic(optional=false)
    private String email;

    @Basic(optional=false)
    private String passwordHash;

    @OneToMany(mappedBy="author")
    private Set<Feedback> feedback;

    public Person() {}

    public Person(String name, String email, String passwordHash) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Person that = (Person) obj;
        return Objects.equals(this.id, that.id);
    }

    public int hashCode() {
        return Objects.hash(id);
    }

}
