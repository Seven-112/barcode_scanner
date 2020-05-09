package com.asd.mentesaudvel.model;

public class Item {

    String name;
    int type;
    String quantity;
    String favourite;

    public Item(String name, int type, String quantity, String favourite ) {
        this.name=name;
        this.type=type;
        this.quantity=quantity;
        this.favourite=favourite;

    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getquantity() {
        return quantity;
    }

    public String getfavourite() {
        return favourite;
    }

}