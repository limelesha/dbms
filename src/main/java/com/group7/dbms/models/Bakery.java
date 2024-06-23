package com.group7.dbms;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import org.hibernate.annotations.Check;


@Entity
@Check(constraints="openTime < closeTime")
public class Bakery {

    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional=false)
    private String address;

    @Basic(optional=false)
    private LocalTime openTime;

    @Basic(optional=false)
    private LocalTime closeTime;

    @OneToMany(mappedBy="location")
    private Set<Employee> staff;

    public Bakery() {}

    public Bakery(String address, LocalTime openTime, LocalTime closeTime) {
        this.address = address;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public LocalTime getOpenTime() { return openTime; }
    public void setOpenTime(LocalTime openTime) { this.openTime = openTime; }
    public LocalTime getCloseTime() { return closeTime; }
    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }
    public Set<Employee> getStaff() { return staff; }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Bakery that = (Bakery) obj;
        return Objects.equals(this.id, that.id);
    }

    public int hashCode() {
        return Objects.hash(id);
    }

}
