package com.group7.dbms;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
public class Customer {

    @RepresentationIncluded(RepresentationType.PARTIAL)
    @Id
    @ManyToOne(fetch=FetchType.LAZY)
    @OnDelete(action=OnDeleteAction.CASCADE)
    private Person person;

    @RepresentationIncluded(RepresentationType.FULL)
    private String deliveryAddress;

    @OneToMany(mappedBy="customer")
    private Set<Order> orders;

    public Customer() {}

    public Customer(Person person, String deliveryAddress) {
        this.person = person;
        this.deliveryAddress = deliveryAddress;
    }

    public Customer(Person person) {
        this.person = person;
    }

    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    public Set<Order> getOrders() { return orders; }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Customer that = (Customer) obj;
        return Objects.equals(this.person, that.person);
    }

    public int hashCode() {
        return Objects.hash(person);
    }

}
