package com.example.project1301.model;

import java.io.Serializable;
import java.util.List;

public class ItemOrder implements Serializable {
    private String id;
    private List<ItemCart> listItemCart;
    private long total;
    private Payment payment;
    private String dateCreate;
    private String state;

    private Shipment shipment;

    public ItemOrder(String id, List<ItemCart> listItemCart, long total, Payment payment, String dateCreate, String state, Shipment shipment ) {
        this.id = id;
        this.listItemCart = listItemCart;
        this.total = total;
        this.payment = payment;
        this.dateCreate = dateCreate;
        this.state = state;
        this.shipment = shipment;
    }

    public ItemOrder() {
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ItemCart> getListItemCart() {
        return listItemCart;
    }

    public void setListItemCart(List<ItemCart> listItemCart) {
        this.listItemCart = listItemCart;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
