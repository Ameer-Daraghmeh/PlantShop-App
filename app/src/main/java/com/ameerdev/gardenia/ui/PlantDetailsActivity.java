package com.ameerdev.gardenia.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ameerdev.gardenia.R;
import com.ameerdev.gardenia.models.Plant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import util.GardeniaApi;

public class PlantDetailsActivity extends AppCompatActivity {

    GardeniaApi gardeniaApi = GardeniaApi.getInstance();
    Plant plant;
    TextView tv_product_details_title,
            tv_product_details_price,
            tv_plant_height,
            tv_sun_light,
            tv_water,
            tv_fertilization,
            tv_description
                    ;
    ImageView image;
    Button btn_add_to_cart;
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
        image = findViewById(R.id.iv_product_detail_image);
        btn_add_to_cart = findViewById(R.id.btn_add_to_cart);


        btn_add_to_cart.setOnClickListener(view -> {
            //Add plant to cart list
            gardeniaApi.addPlantToCart();
            //go to cart activity
            startActivity(new Intent(PlantDetailsActivity.this,CartListActivity.class));
        });


        plant = gardeniaApi.getClickedPlant();

        /**
         * Load and show clicked plant Informationnnnn
         */
        tv_product_details_title.setText(plant.getName());
        tv_product_details_price.setText(plant.getPrice());
        tv_plant_height.setText(plant.getPlant_height());
        tv_sun_light.setText(plant.getSun_light());
        tv_water.setText(plant.getWater());
        tv_fertilization.setText(plant.getFertilization());
        tv_description.setText(plant.getDescription());

        /**
         * Load and show clicked plant Imageeeeeeeee
         */
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        StorageReference load = storageRef.child("PlantViewImg").child(plant.getPlant_view_img());

        load.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                // Pass it to Picasso to download, show in ImageView and caching
                Picasso.get().load(uri.toString()).into(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


    }
}