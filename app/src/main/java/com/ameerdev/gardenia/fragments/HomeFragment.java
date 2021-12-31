package com.ameerdev.gardenia.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ameerdev.gardenia.R;
import com.ameerdev.gardenia.adapter.PlantRecyclerViewAdapter;
import com.ameerdev.gardenia.models.Plant;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {


    private ArrayList<Plant> plantList;
    RecyclerView mRecyclerView;
    PlantRecyclerViewAdapter mAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        plantList = new ArrayList<Plant>();


        Plant plant1 = new Plant("tomato",10);
        Plant plant2 = new Plant("sosa",4);

        plantList.add(plant1);
        plantList.add(plant2);

        mRecyclerView = view.findViewById(R.id.mostpop_plant_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);


       mAdapter = new PlantRecyclerViewAdapter(plantList,this.getContext());
       mRecyclerView.setAdapter(mAdapter);

        // Inflate the layout for this fragment
        return view;
    }
}