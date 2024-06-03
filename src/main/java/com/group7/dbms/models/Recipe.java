package com.group7.dbms;

import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
public class Recipe {

    @RepresentationIncluded(RepresentationType.IDENTIFIER)
    @Id
    @GeneratedValue
    private Long id;

    @RepresentationIncluded(RepresentationType.PARTIAL)
    @ManyToOne(fetch=FetchType.LAZY)
    @OnDelete(action=OnDeleteAction.CASCADE)
    private Product product;

    @RepresentationIncluded(RepresentationType.FULL)
    @Basic(optional=false)
    private String instructions;

    public Recipe() {}

    public Recipe(Product product, String instructions) {
        this.product = product;
        this.instructions = instructions;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Recipe that = (Recipe) obj;
        return Objects.equals(this.id, that.id);
    }

    public int hashCode() {
        return Objects.hash(id);
    }

}
