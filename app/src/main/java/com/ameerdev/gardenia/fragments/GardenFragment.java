package com.ameerdev.gardenia.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ameerdev.gardenia.R;
import com.ameerdev.gardenia.adapter.MyPlantsAdapter;
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

    private  static  ArrayList<Plant> plantList= new ArrayList<Plant>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView mRecyclerView;
    StorageReference load;
    static FirebaseStorage storage = FirebaseStorage.getInstance();
    static StorageReference storageRef = storage.getReference();
    MyPlantsAdapter mAdapter;

    static int count = 0;
    public static void setCount(int c){
        count = c;
    }
    public GardenFragment() {
        // Required empty public
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_garden, container, false);
        mAdapter = new MyPlantsAdapter(plantList,this.getContext());
        getPlantList();
        mRecyclerView = view.findViewById(R.id.my_plants_rv);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        // Inflate the layout for this fragment
        return view;
    }


    void getPlantList(){
    if (count == 0 ) {
        count++;
        plantList.clear();
        plantList = new ArrayList<>();
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
                                            plant.setUri(uri.toString());
                                            if(!plantList.contains(plant)){
                                                plantList.add(plant);
                                                mAdapter.notifyDataSetChanged();
                                            }
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