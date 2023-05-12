package com.hka.shop.model;

public class PurchasedItem {
    private int id;
    private String name;
    private String price;
    private String image;
    private int amount;

    public PurchasedItem(int id, String name, String price, String image, int amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount += amount;
    }
}
