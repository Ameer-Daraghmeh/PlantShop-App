package com.ameerdev.gardenia.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ameerdev.gardenia.R;
import com.ameerdev.gardenia.adapter.MyPlantsAdapter;
import com.ameerdev.gardenia.adapter.PlantRecyclerViewAdapter;
import com.ameerdev.gardenia.models.Plant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import util.GardeniaApi;


public class GardenFragment extends Fragment {

    private  static final ArrayList<Plant> plantList= new ArrayList<Plant>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView mRecyclerView;
    static MyPlantsAdapter mAdapter;
    StorageReference load;
    static FirebaseStorage storage = FirebaseStorage.getInstance();
    static StorageReference storageRef = storage.getReference();

    int count =0;
    public GardenFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_garden, container, false);
        getPlantList();
        mRecyclerView = view.findViewById(R.id.my_plants_rv);
        mAdapter = new MyPlantsAdapter(plantList,this.getContext());
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);


        // Inflate the layout for this fragment
        return view;
    }

    void emptyPlantList(ArrayList<Plant>plntList){
        for (int i =0 ; i <plntList.size();i++){
            plantList.remove(i);
        }
    }
    void getPlantList(){
        if (count == 0){
            count++;
            db.collection("UserPlants")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot plants : task.getResult()) {

                                    if (plants.getData().get("userId").toString().
                                            equals(GardeniaApi.getInstance().getUserId())) {
                                        Plant plant = plants.toObject(Plant.class);
                                        load = storageRef.child("PlantProfileImg").child(plant.getPlant_profile_img());
                                        load.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                // Got the download URL for 'users/me/profile.png'
                                                // Pass it to Picasso to download, show in ImageView and caching
                                                plant.setUri(uri.toString());
                                                plantList.add(plant);
                                                mAdapter.notifyDataSetChanged();
                                            }
                                        });

                                    }

                                }
                            } else {
                                Log.d("suuu", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
    }
}