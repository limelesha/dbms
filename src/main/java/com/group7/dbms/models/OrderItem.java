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
public class OrderItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @OnDelete(action=OnDeleteAction.CASCADE)
    private Order order;

    @ManyToOne(fetch=FetchType.LAZY)
    @OnDelete(action=OnDeleteAction.CASCADE)
    private Product product;

    @Basic(optional=false)
    private Integer quantity;

    public OrderItem() {}

    public OrderItem(Order order, Product product, int quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    public OrderItem(Order order, Product product) {
        this(order, product, 1);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        OrderItem that = (OrderItem) obj;
        return Objects.equals(this.id, that.id);
    }

    public int hashCode() {
        return Objects.hash(id);
    }

}
