package com.example.project1301.model;

public class OptionData {
    private int img;
    private String name;

    public OptionData(int img, String name) {
        this.img = img;
        this.name = name;
    }

    public OptionData() {
    }

    public int getImg() {
        return img;
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
}

