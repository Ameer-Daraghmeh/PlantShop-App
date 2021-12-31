package com.ameerdev.gardenia.adapter;

import android.content.Context;
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

import java.util.ArrayList;


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

    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }


    /*****************************
        View Holder inner class
     *****************************/

    public class PlantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView plant_img;
        public TextView plant_name, plant_price;


        //OnTodoClickListener onTodoClickListener;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            //plant_img = itemView.findViewById(R.id.plant_img);
            plant_name =  itemView.findViewById(R.id.plant_name);
            plant_price =  itemView.findViewById(R.id.plant_price);
            //this.onTodoClickListener = todoClickListener;


            //itemView.setOnClickListener(this);
            //radioButton.setOnClickListener(this);

        }

        public void bindPlant(Plant plant) {
            plant_name.setText(plant.getName());
            plant_price.setText(plant.getPrice());
        }

        @Override
        public void onClick(View view) {
//            Task currTask = taskList.get(getAdapterPosition());
//
//            int id = view.getId();
//            if (id == R.id.todo_row_layout) {
//                onTodoClickListener.onTodoClick(currTask);
//            }else if (id == R.id.todo_radio_button) {
//                onTodoClickListener.onTodoRadioButtonClick(currTask);
//
//            }

        }
    }


}
