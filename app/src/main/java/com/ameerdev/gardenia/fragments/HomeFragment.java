package com.ameerdev.gardenia.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ameerdev.gardenia.R;
import com.ameerdev.gardenia.adapter.PlantRecyclerViewAdapter;
import com.ameerdev.gardenia.models.Plant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import util.GardeniaApi;


public class HomeFragment extends Fragment {


    private static final ArrayList<Plant> plantList= new ArrayList<Plant>();
    private RecyclerView mRecyclerView;
    private PlantRecyclerViewAdapter mAdapter;

    private static HomeFragment instance;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    static int count = 0;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        getPlantList();

        mRecyclerView = view.findViewById(R.id.mostpop_plant_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

       mAdapter = new PlantRecyclerViewAdapter(plantList,this.getContext());
       mRecyclerView.setAdapter(mAdapter);
       mRecyclerView.suppressLayout(true);

        // Inflate the layout for this fragment
        return view;
    }

    void getPlantList(){

        if (count == 0){
            count++;
            db.collection("Indoor")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.d("suuu", document.getId() + " => " + document.getData());

                                    String price =  document.getData().get("price").toString();
                                    String name = document.getData().get("name").toString();
                                    String water =  document.getData().get("water").toString();
                                    String fertilization = document.getData().get("fertilization").toString();
                                    String description = document.getData().get("description").toString();
                                    String plant_height = document.getData().get("plant_height").toString();
                                    String sun_light= document.getData().get("sun_light").toString();

                                    Plant plant = new Plant();
                                    plant.setName(name);
                                    plant.setPrice(price);
                                    plant.setDescription(description);
                                    plant.setFertilization(fertilization);
                                    plant.setWater(water);
                                    plant.setSun_light(sun_light);
                                    plant.setPlant_height(plant_height);

                                    plantList.add(plant);

                                }
                            } else {
                                Log.d("suuu", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }


    }


}