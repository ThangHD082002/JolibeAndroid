package com.example.project1301.model;

public class Food {
    private int img;
    private String name, price, moTa, categoy;

    public Food(int img, String name, String price, String moTa, String categoy) {
        this.img = img;
        this.name = name;
        this.price = price;
        this.moTa = moTa;
        this.categoy = categoy;
    }

    public Food(int img, String name, String price, String moTa) {
        this.img = img;
        this.name = name;
        this.price = price;
        this.moTa = moTa;
    }

    public Food() {
    }

    public int getImg() {
        return img;
    }

    public String getCategoy() {
        return categoy;
    }

    public void setImg(int img) {
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
