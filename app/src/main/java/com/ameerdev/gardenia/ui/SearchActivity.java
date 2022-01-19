package com.ameerdev.gardenia.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ameerdev.gardenia.R;
import com.ameerdev.gardenia.adapter.PlantRecyclerViewAdapter;
import com.ameerdev.gardenia.models.Plant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;

import util.GardeniaApi;

public class SearchActivity extends AppCompatActivity {

    private static ArrayList<Plant> plantList= new ArrayList<>();
    private RecyclerView mRecyclerView;
    private  PlantRecyclerViewAdapter mAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor);
        getPlantList();
        mAdapter = new PlantRecyclerViewAdapter(plantList,this);
        mRecyclerView = findViewById(R.id.rv_indoor);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

    }
    void getPlantList(){

        Plant plant = GardeniaApi.getInstance().getSearchPlant();
        if (plantList.size()>0){
            plantList.remove(0);
        }
        plantList.add(plant);
    }

}