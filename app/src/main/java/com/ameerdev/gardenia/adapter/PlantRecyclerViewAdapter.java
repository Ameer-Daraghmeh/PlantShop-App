package com.ameerdev.gardenia.adapter;

import android.content.Context;
import android.content.Intent;
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

        holder.bindPlant(plantList.get(position));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here You Do Your Click Magic

                GardeniaApi.setClickedPlant(plantList.get(holder.getAdapterPosition()));

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

        private static ImageView plant_img;
        private final Context context;
        private TextView plant_name, plant_price;

        View mView;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);

            plant_name =  itemView.findViewById(R.id.plant_name);
            plant_price =  itemView.findViewById(R.id.plant_price);
            plant_img = itemView.findViewById(R.id.plant_img);




            context = itemView.getContext();
            mView = itemView;


        }

        public void bindPlant(Plant plant) {
            plant_name.setText(plant.getName());
            plant_price.setText(plant.getPrice());

            FirebaseStorage storage = FirebaseStorage.getInstance();

            // Create a storage reference from our app
            StorageReference storageRef = storage.getReference();

            StorageReference load = storageRef.child(plant.getPlant_profile_img());

            load.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    // Pass it to Picasso to download, show in ImageView and caching
                    Picasso.get().load(uri.toString()).into(plant_img);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

        /*
         Use Picasso library to download and show image
         */
//            Log.d("sss",plant.getPlant_profile_img());
//            Picasso.get()
//                    .load(plant.getPlant_profile_img())
//                    .placeholder(R.drawable.palor_palm_ppimg)
//                    .fit()
//                    .into(PlantViewHolder.plant_img);
//
        }

        @Override
        public void onClick(View view) {


        }
    }


}
