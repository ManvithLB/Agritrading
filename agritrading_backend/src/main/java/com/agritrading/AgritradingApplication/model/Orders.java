package com.agritrading.AgritradingApplication.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;

import java.util.Date;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int order_Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customers customer;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_id")
    private Products product;


    private int quantity;
    private int total_Price;
    @CreationTimestamp
    private Date order_date;
    private String order_status;



    public int getOrder_Id() {
        return order_Id;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "order_Id=" + order_Id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", total_Price=" + total_Price +
                ", order_date=" + order_date +
                ", order_status='" + order_status + '\'' +
                '}';
    }

    public void setOrder_Id(int order_Id) {
        this.order_Id = order_Id;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal_Price() {
        return total_Price;
    }

    public void setTotal_Price(int total_Price) {
        this.total_Price = total_Price;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }





}
