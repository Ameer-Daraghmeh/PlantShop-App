package com.ameerdev.gardenia.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import util.GardeniaApi;

public class PlantRecyclerViewAdapter extends RecyclerView.Adapter<PlantRecyclerViewAdapter.PlantViewHolder>{

    private  ArrayList<Plant> plantList = new ArrayList<>() ;
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

                Intent intent = new Intent(context ,PlantDetailsActivity.class);
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
        View mView;


        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);

            plant_name =  itemView.findViewById(R.id.plant_name);
            plant_price =  itemView.findViewById(R.id.plant_price);
            plant_img = itemView.findViewById(R.id.plant_img);


            context = itemView.getContext();
            mView = itemView;

        }

        public void bindPlant(Plant plant) throws IOException {
            Picasso.get()
                    .load(plant.getUri())
                    .placeholder(R.drawable.plant_img)
                    .fit()
                    .into(plant_img);
            plant_name.setText(plant.getName());
            plant_price.setText(plant.getPrice());
        }

        @Override
        public void onClick(View view) {

        }
    }
}
