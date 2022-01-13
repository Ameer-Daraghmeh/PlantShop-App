package com.ameerdev.gardenia.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.ameerdev.gardenia.ui.CartListActivity;
import com.ameerdev.gardenia.ui.PlantDetailsActivity;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;

import util.GardeniaApi;


public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.PlantViewHolder>{

    private ArrayList<Plant> plantList = new ArrayList<>() ;
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
            plantList.remove(position);


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


        private ImageView plant_img;
        private TextView plant_name, plant_price,
        tv_subtotal,tv_shipping_charge,tv_total  ;
        ImageButton deletFromCart;
        static int  subtotal = 0, shipping = 0, total = 0;



        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        View mView;
        StorageReference  load;


        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);

            plant_name =  itemView.findViewById(R.id.tv_cart_item_title);
            plant_price =  itemView.findViewById(R.id.tv_cart_item_price);
            plant_img = itemView.findViewById(R.id.plant_img);
            deletFromCart = itemView.findViewById(R.id.ib_delete_cart_item);
//            tv_subtotal = itemView.findViewById(R.id.tv_sub_total);
//            tv_shipping_charge = itemView.findViewById(R.id.tv_shipping_charge);
//            tv_total = itemView.findViewById(R.id.tv_total_amount);

            context = itemView.getContext();
            mView = itemView;


        }

        public void bindPlant(Plant plant) throws IOException {
            plant_name.setText(plant.getName());
            plant_price.setText(plant.getPrice());
       //     tv_subtotal.setText("111");
           // tv_shipping_charge.setText(String.format("%d",shipping));
//            tv_total.setText(total);

        }





        @Override
        public void onClick(View view) {


        }
    }


}
