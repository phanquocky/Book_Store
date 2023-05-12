package com.hka.shop.model;

public class Item {
    private int id;
    private String name;
    private String image;
    private String price;
    private String description;
    private int type;

    public Item(int id, String name, String image, String price, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
