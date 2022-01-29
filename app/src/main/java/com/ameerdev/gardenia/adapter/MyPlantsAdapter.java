package com.ameerdev.gardenia.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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


public class MyPlantsAdapter extends RecyclerView.Adapter<MyPlantsAdapter.PlantViewHolder>{

    private static ArrayList<Plant> plantList = new ArrayList<>() ;
    Context context;

    public MyPlantsAdapter(ArrayList<Plant> plantList, Context context) {
        this.plantList = plantList;
        this.context = context;


    }
    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plant_row_my_plants, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {

        try {
            holder.bindPlant(plantList.get(position));
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.plant_Setting.setOnClickListener(view -> {
            holder.btn_delete_plant.setVisibility(View.VISIBLE);
        });
        holder.ll.setOnClickListener(view -> {
            holder.btn_delete_plant.setVisibility(View.GONE);

        });
        holder.btn_delete_plant.setOnClickListener(view -> {
            if(plantList.contains(plantList.get(position))){
                plantList.remove(position);
                notifyDataSetChanged();
            }
            holder.btn_delete_plant.setVisibility(View.GONE);
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here You Do Your Click Magic


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

        private final Context context;

        TextView tv_plant_name,tv_sun, tv_water;
        ImageView iv_plant;
        View mView , plant_Setting;
        Button btn_delete_plant;
        LinearLayout ll;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_plant_name =  itemView.findViewById(R.id.tv_plant_name);
            tv_sun = itemView.findViewById(R.id.tv_sun);
            tv_water = itemView.findViewById(R.id.tv_water);
            iv_plant = itemView.findViewById(R.id.iv_plant);
            btn_delete_plant = itemView.findViewById(R.id.btn_delete_plant);
            plant_Setting = itemView.findViewById(R.id.iv_plant_settings);
            ll = itemView.findViewById(R.id.linerlayt);

            context = itemView.getContext();
            mView = itemView;


        }

        public void bindPlant(Plant plant) throws IOException {
            tv_plant_name.setText(plant.getName());
            tv_water.setText(plant.getPlant_water());
            tv_sun.setText(plant.getPlant_sun());

            Picasso.get()
                    .load(plant.getUri())
                    .placeholder(R.drawable.plant_img)
                    .fit()
                    .into(iv_plant);
        }

        @Override
        public void onClick(View view) {


        }
    }


}
