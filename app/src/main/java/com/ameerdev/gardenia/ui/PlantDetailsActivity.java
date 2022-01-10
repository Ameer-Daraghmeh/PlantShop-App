package com.ameerdev.gardenia.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.ameerdev.gardenia.R;
import com.ameerdev.gardenia.models.Plant;

import util.GardeniaApi;

public class PlantDetailsActivity extends AppCompatActivity {

    Plant plant;
    TextView tv_product_details_title,
            tv_product_details_price,
            tv_plant_height,
            tv_sun_light,
            tv_water,
            tv_fertilization,
            tv_description
                    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);

        tv_product_details_title = findViewById(R.id.tv_product_details_title);
        tv_product_details_price = findViewById(R.id.tv_product_details_price);
        tv_plant_height = findViewById(R.id.tv_plant_height);
        tv_sun_light = findViewById(R.id.tv_sun_light);
        tv_water = findViewById(R.id.tv_water);
        tv_fertilization = findViewById(R.id.tv_fertilization);
        tv_description = findViewById(R.id.tv_product_details_description);



        plant = GardeniaApi.getClickedPlant();

        tv_product_details_title.setText(plant.getName());
        tv_product_details_price.setText(plant.getPrice());
        tv_plant_height.setText(plant.getPlant_height());
        tv_sun_light.setText(plant.getSun_light());
        tv_water.setText(plant.getWater());
        tv_fertilization.setText(plant.getFertilization());
        tv_description.setText(plant.getDescription());



    }
}