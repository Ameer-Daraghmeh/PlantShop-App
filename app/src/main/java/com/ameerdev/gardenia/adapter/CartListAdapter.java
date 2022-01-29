package com.ameerdev.gardenia.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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


public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.PlantViewHolder>{

    private static ArrayList<Plant> plantList = new ArrayList<>() ;
    Context context;

    public CartListAdapter(ArrayList<Plant> plantList, Context context) {
        this.plantList = plantList;
        this.context = context;


    }
    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_row, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {

        try {
            holder.bindPlant(plantList.get(position));
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.deletFromCart.setOnClickListener(view -> {
            if (plantList.contains(plantList.get(position)))
            {
                plantList.remove(position);
                notifyDataSetChanged();
            }
        });

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

        private final Context context;

        private TextView plant_name, plant_price;
        ImageView iv_cart_item_image;
        ImageButton deletFromCart;
        View mView;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);

            plant_name =  itemView.findViewById(R.id.tv_cart_item_title);
            plant_price =  itemView.findViewById(R.id.tv_cart_item_price);
            deletFromCart = itemView.findViewById(R.id.ib_delete_cart_item);
            iv_cart_item_image = itemView.findViewById(R.id.iv_cart_item_image);

            context = itemView.getContext();
            mView = itemView;


        }

        public void bindPlant(Plant plant) throws IOException {
            Picasso.get()
                    .load(plant.getUri())
                    .placeholder(R.drawable.plant_img)
                    .fit()
                    .into(iv_cart_item_image);
            plant_name.setText(plant.getName());
            plant_price.setText(plant.getPrice());
        }

        @Override
        public void onClick(View view) {


        }
    }


}
