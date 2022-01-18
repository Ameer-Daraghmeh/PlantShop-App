package util;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.ameerdev.gardenia.adapter.PlantRecyclerViewAdapter;
import com.ameerdev.gardenia.models.Plant;
import com.ameerdev.gardenia.ui.CartListActivity;
import com.ameerdev.gardenia.ui.PlantDetailsActivity;

import java.util.ArrayList;

public class GardeniaApi extends Application {
    private String username;
    private String userId;
    private static GardeniaApi instance;
    private  Plant clickedPlant;
    private ArrayList<Plant>cartList = new ArrayList<>();



    public static GardeniaApi getInstance() {
        if (instance == null)
            instance = new GardeniaApi();
        return instance;

    }

    public void setCartList(ArrayList<Plant> cartList) {
        this.cartList = cartList;
    }

    public GardeniaApi(){}

    public  ArrayList<Plant> getCartList() {
        return cartList;
    }

    public  void addPlantToCart(){
        cartList.add(clickedPlant);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public  Plant getClickedPlant() {
        return clickedPlant;
    }

    public  void setClickedPlant(Plant clickedPlant) {
        this.clickedPlant = clickedPlant;
    }
}
