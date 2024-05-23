package com.example.project1301.model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable{
    private List<ItemOrder> listItemOrder;


    public Order(List<ItemOrder> listItemOrder) {
        this.listItemOrder = listItemOrder;
    }

    public Order() {
    }

    public List<ItemOrder> getListItemOrder() {
        return listItemOrder;
    }

    public void setListItemOrder(List<ItemOrder> listItemOrder) {
        this.listItemOrder = listItemOrder;
    }
}
