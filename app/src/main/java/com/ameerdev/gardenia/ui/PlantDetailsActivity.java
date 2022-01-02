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
            tv_product_details_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);

        tv_product_details_title = findViewById(R.id.tv_product_details_title);
        tv_product_details_price = findViewById(R.id.tv_product_details_price);

        plant = GardeniaApi.getClickedPlant();

        tv_product_details_title.setText(plant.getName());
        tv_product_details_price.setText(plant.getPrice());
    }
}