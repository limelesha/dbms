package com.group7.dbms;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class Employee {

    @RepresentationIncluded(RepresentationType.PARTIAL)
    @Id
    @ManyToOne(fetch=FetchType.LAZY)
    private Person person;

    @RepresentationIncluded(RepresentationType.PARTIAL)
    @Enumerated(EnumType.STRING)
    private Role role;

    @RepresentationIncluded(RepresentationType.FULL)
    @ManyToOne(fetch=FetchType.LAZY)
    private Bakery location;

    public Employee() {}

    public Employee(Person person, Role role, Bakery location) {
        this.person = person;
        this.role = role;
        this.location = location;
    }

    public Employee(Person person) {
        this.person = person;
    }

    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public Bakery getLocation() { return location; }
    public void setLocation(Bakery location) { this.location = location; }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Employee that = (Employee) obj;
        return Objects.equals(this.person, that.person);
    }

    public int hashCode() {
        return Objects.hash(person);
    }

}
