package util;

import android.app.Application;
import android.content.Intent;

import com.ameerdev.gardenia.adapter.PlantRecyclerViewAdapter;
import com.ameerdev.gardenia.models.Plant;
import com.ameerdev.gardenia.ui.PlantDetailsActivity;

public class GardeniaApi extends Application {
    private String username;
    private String userId;
    private static GardeniaApi instance;
    private static Plant clickedPlant;

    public static GardeniaApi getInstance() {
        if (instance == null)
            instance = new GardeniaApi();
        return instance;

    }

    public void onHomePlantClick(){
        Intent intent = new Intent(GardeniaApi.this , PlantDetailsActivity.class);
        startActivity(intent);
    }

    public GardeniaApi(){}


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

    public static Plant getClickedPlant() {
        return clickedPlant;
    }

    public static void setClickedPlant(Plant clickedPlant) {
        GardeniaApi.clickedPlant = clickedPlant;
    }
}
