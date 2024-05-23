package com.example.project1301.model;

import java.io.Serializable;

public class ItemCart implements Serializable {
    private String id;
    private Food food;
    private int sl;

    public ItemCart(String id, Food food, int sl) {
        this.id = id;
        this.food = food;
        this.sl = sl;
    }

    public ItemCart() {
    }

    public String getId() {
        return id;
    }

    public Food getFood() {
        return food;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public int getSl() {
        return sl;
    }
}
