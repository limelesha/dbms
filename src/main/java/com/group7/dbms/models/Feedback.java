package com.group7.dbms;

import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(
    uniqueConstraints={
        @UniqueConstraint(columnNames={"product_id", "author_id"})
    }
)
public class Feedback {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER)
    @OnDelete(action=OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne(fetch=FetchType.EAGER)
    @OnDelete(action=OnDeleteAction.CASCADE)
    private Person author;

    @Check(constraints="rating between 1 and 5")
    @Basic(optional=false)
    private Integer rating;

    private String comment;

    public Feedback() {}

    public Feedback(
        Product product,
        Person author,
        int rating,
        String comment
    ) {
        this.product = product;
        this.author = author;
        this.rating = rating;
        this.comment = comment;
    }

    public Feedback(Product product, Person author, int rating) {
        this.product = product;
        this.author = author;
        this.rating = rating;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Person getAuthor() { return author; }
    public void setAuthor(Person author) { this.author = author; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Feedback that = (Feedback) obj;
        return Objects.equals(this.id, that.id);
    }

    public int hashCode() {
        return Objects.hash(id);
    }

}
