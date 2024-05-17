package com.group7.dbms;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
public class Product {

    @RepresentationIncluded(RepresentationType.PARTIAL)
    @Id
    @GeneratedValue
    private Long id;

    @RepresentationIncluded(RepresentationType.PARTIAL)
    @Basic(optional=false)
    private String name;

    @RepresentationIncluded(RepresentationType.FULL)
    @Basic(optional=false)
    private String description;

    @RepresentationIncluded(RepresentationType.PARTIAL)
    @Basic(optional=false)
    private BigDecimal price;

    public Product() {}

    public Product(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
    }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Product that = (Product) obj;
        return Objects.equals(this.id, that.id);
    }

    public int hashCode() {
        return Objects.hash(id);
    }

}
