package com.example.project1301.model;

import java.io.Serializable;

public class Data implements Serializable {

    private String id;
    private String img;
    private String name, price, moTa, categoy;

    public Data(String id, String img, String name, String price, String moTa, String categoy) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.price = price;
        this.moTa = moTa;
        this.categoy = categoy;
    }

    public Data(String img, String name, String price, String moTa, String category) {
        this.img = img;
        this.name = name;
        this.price = price;
        this.moTa = moTa;
        this.categoy = category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCategoy(String categoy) {
        this.categoy = categoy;
    }

    public String getId() {
        return id;
    }

    public Data() {
    }

    public String getImg() {
        return img;
    }

    public String getCategoy() {
        return categoy;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
