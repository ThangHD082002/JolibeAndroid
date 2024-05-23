package com.example.project1301.model;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class UserOrder implements Serializable {
    String uuid;
    String name;
    String phone;
    String mail;
    String pass;
    String address;

    Order order;


    public UserOrder(String uuid, String name, String phone, String mail, String pass, String address, Order order) {
        this.uuid = uuid;
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.pass = pass;
        this.address = address;
        this.order = order;
    }

    public UserOrder() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }

    public String getPass() {
        return pass;
    }

    public String getAddress() {
        return address;
    }

}
