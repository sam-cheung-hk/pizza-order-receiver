package com.challenge.order.receiver.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pizza_order")
public class PizzaOrder {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "order_ref_no")
    private String orderReferenceNumber;

    @Column(name = "order_time")
    private ZonedDateTime orderTime;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "creation_time", updatable = false)
    @CreationTimestamp
    private ZonedDateTime creationTime;

    @Column(name = "modification_time")
    @UpdateTimestamp
    private ZonedDateTime modificationTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pizzaOrder", cascade = { CascadeType.ALL })
    private List<PizzaOrderDetail> orderDetails;

    // Getters & Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderReferenceNumber() {
        return orderReferenceNumber;
    }

    public void setOrderReferenceNumber(String orderReferenceNumber) {
        this.orderReferenceNumber = orderReferenceNumber;
    }

    public ZonedDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(ZonedDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(ZonedDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public ZonedDateTime getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(ZonedDateTime modificationTime) {
        this.modificationTime = modificationTime;
    }

    public List<PizzaOrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<PizzaOrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void addOrderDetail(PizzaOrderDetail orderDetail) {
        if (this.orderDetails == null) {
            this.orderDetails = new ArrayList<>();
        }
        this.orderDetails.add(orderDetail);
    }

    @Override
    public String toString() {
        return "PizzaOrder{" +
                "id='" + id + '\'' +
                ", orderReferenceNumber='" + orderReferenceNumber + '\'' +
                ", orderTime=" + orderTime +
                ", totalPrice=" + totalPrice +
                ", creationTime=" + creationTime +
                ", modificationTime=" + modificationTime +
                '}';
    }
}
