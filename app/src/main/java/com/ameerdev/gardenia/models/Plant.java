package com.ameerdev.gardenia.models;

public class Plant {
    String name;
    String water ,
            fertilization ,
            description,
            plant_height,
            sun_light;
    String plant_profile_img;
    String plant_view_img;
    String price;
    String id;
    String uri;
    String plant_sun;
    String plant_water;
    String type;

    public Plant() {
        name=null;
        water=null;
        fertilization=null;
        description=null;
        plant_height=null;
        sun_light=null;
        plant_profile_img = null;
        plant_view_img=null;
        price=null;
        id=null;
        uri=null;
        plant_sun=null;
        plant_water=null;
        type=null;
    }

    public String getPlant_sun() {
        return plant_sun;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPlant_sun(String plant_sun) {
        this.plant_sun = plant_sun;
    }

    public String getPlant_water() {
        return plant_water;
    }

    public void setPlant_water(String plant_water) {
        this.plant_water = plant_water;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    public String getPrice2() {
        return price;
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
