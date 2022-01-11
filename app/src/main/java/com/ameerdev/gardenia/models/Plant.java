package com.ameerdev.gardenia.models;

public class Plant {
    String name;
    String water , fertilization , description, plant_height, sun_light;
    String plant_profile_img;
    String plant_view_img;
    String price;

    public Plant() {
    }

    public String getPlant_profile_img() {
        return plant_profile_img;
    }

    public void setPlant_profile_img(String plant_profile_img) {
        this.plant_profile_img = plant_profile_img;
    }

    public String getPlant_view_img() {
        return plant_view_img;
    }

    public void setPlant_view_img(String plant_view_img) {
        this.plant_view_img = plant_view_img;
    }

    public String getName() {
        return name.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return "$"+price+".00";
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getFertilization() {
        return fertilization;
    }

    public void setFertilization(String fertilization) {
        this.fertilization = fertilization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlant_height() {
        return plant_height;
    }

    public void setPlant_height(String plant_height) {
        this.plant_height = plant_height;
    }

    public String getSun_light() {
        return sun_light;
    }

    public void setSun_light(String sun_light) {
        this.sun_light = sun_light;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }


}
