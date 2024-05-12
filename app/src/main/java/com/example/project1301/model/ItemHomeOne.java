package com.example.project1301.model;

import java.io.Serializable;

public class ItemHomeOne implements Serializable {
    private int img;
    private String name;

    public ItemHomeOne() {
    }

    public ItemHomeOne(int img, String name) {
        this.img = img;
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setName(String name) {
        this.name = name;
    }
}
