package util;

import android.app.Application;

public class GardeniaApi extends Application {
    private String username;
    private String userId;
    private static GardeniaApi instance;

    public static GardeniaApi getInstance() {
        if (instance == null)
            instance = new GardeniaApi();
        return instance;

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
}
