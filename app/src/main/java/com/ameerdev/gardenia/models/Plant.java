package com.ameerdev.gardenia.models;

public class Plant {
    String name;
    int price;

    @Override
    public String toString() {
        return "Plant{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public Plant() {
    }

    public Plant(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price+".00$";
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
