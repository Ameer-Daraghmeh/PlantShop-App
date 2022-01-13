package com.ameerdev.gardenia.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ameerdev.gardenia.R;
import com.ameerdev.gardenia.models.Plant;
import com.ameerdev.gardenia.ui.PlantDetailsActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import util.GardeniaApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class PlantRecyclerViewAdapter extends RecyclerView.Adapter<PlantRecyclerViewAdapter.PlantViewHolder>{

    private ArrayList<Plant> plantList = new ArrayList<>() ;
    Context context;



    public PlantRecyclerViewAdapter(ArrayList<Plant> plantList, Context context) {
        this.plantList = plantList;
        this.context = context;


    }



    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plant_row, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {


        try {
            holder.bindPlant(plantList.get(position));
        } catch (IOException e) {
            e.printStackTrace();
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here You Do Your Click Magic

                GardeniaApi gardeniaApi = GardeniaApi.getInstance();
                gardeniaApi.setClickedPlant(plantList.get(holder.getAdapterPosition()));

                Intent intent = new Intent(context , PlantDetailsActivity.class);
                context.startActivity(intent);

            }});

    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }


    /*****************************
        View Holder inner class
     *****************************/

    public static class PlantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView plant_img;
        private final Context context;
        private TextView plant_name, plant_price;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        View mView;
        StorageReference  load;


        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);

            plant_name =  itemView.findViewById(R.id.plant_name);
            plant_price =  itemView.findViewById(R.id.plant_price);
            plant_img = itemView.findViewById(R.id.plant_img);


            context = itemView.getContext();
            mView = itemView;


        }

        public void bindPlant(Plant plant) throws IOException {
            plant_name.setText(plant.getName());
            plant_price.setText(plant.getPrice());

//            load = storageRef.child("PlantProfileImg").child(plant.getPlant_profile_img());
//
//            load.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    // Got the download URL for 'users/me/profile.png'
//                    // Pass it to Picasso to download, show in ImageView and caching
//                    Picasso.get()
//                            .load(uri.toString())
//                            .placeholder(R.drawable.plant_img)
//                            .fit()
//                            .into(PlantViewHolder.plant_img);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    // Handle any errors
//                }
//            });


        }

        public void loadPlantImg(Plant plant) {

//            StorageReference islandRef = storageRef.child("PlantProfileImg").child(plant.getPlant_profile_img());
//
//            final long ONE_MEGABYTE = 1024 * 1024;
//            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                @Override
//                public void onSuccess(byte[] bytes) {
//                    // Data for "images/island.jpg" is returns, use this as needed
//                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                    plant_img.setImageBitmap(bmp);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    // Handle any errors
//                }
//            });

        }


        @Override
        public void onClick(View view) {


        }
    }


}
