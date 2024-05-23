package com.example.project1301.model;

import java.io.Serializable;
import java.util.List;

public class Cart implements Serializable {

    private List<ItemCart> listItemCart;
    private long total;

    public Cart(List<ItemCart> listItemCart, long total) {
        this.listItemCart = listItemCart;
        this.total = total;
    }

    public Cart() {
    }



    public void setListItemCart(List<ItemCart> listItemCart) {
        this.listItemCart = listItemCart;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<ItemCart> getListItemCart() {
        return listItemCart;
    }

    public long getTotal() {
        return total;
    }
}
