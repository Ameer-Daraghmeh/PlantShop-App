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
                                for (QueryDocumentSnapshot plants : task.getResult()) {
                                    //Log.d("suuu", document.getId() + " => " + document.getData());

                                    Plant plant = plants.toObject(Plant.class);

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