package com.ameerdev.gardenia.fragments;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;

import com.ameerdev.gardenia.R;
import com.ameerdev.gardenia.adapter.PlantRecyclerViewAdapter;
import com.ameerdev.gardenia.models.Plant;
import com.ameerdev.gardenia.ui.CartListActivity;
import com.ameerdev.gardenia.ui.IndoorActivity;
import com.ameerdev.gardenia.ui.OutdoorActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class HomeFragment extends Fragment {


    private static final ArrayList<Plant> plantList= new ArrayList<Plant>();
    private static RecyclerView mRecyclerView;
    private static PlantRecyclerViewAdapter mAdapter;

    static FirebaseStorage storage = FirebaseStorage.getInstance();
    static StorageReference storageRef = storage.getReference();

    Button btn_cart;
    StorageReference load;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    static int count = 0;

    ImageView iv_indoor;
    ImageView iv_outdoor;

    public HomeFragment() {
        // Required empty public constructor
        getPlantList();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = view.findViewById(R.id.mostpop_plant_rv);
        mAdapter = new PlantRecyclerViewAdapter(plantList,this.getContext());
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        /**
         * Indoor and out door image view click
         */

        iv_indoor = view.findViewById(R.id.iv_indoor);
        iv_outdoor = view.findViewById(R.id.iv_outdoor);

        iv_indoor.setOnClickListener(view13 -> {
            startActivity(new Intent(this.getActivity(), IndoorActivity.class));
        });
        iv_outdoor.setOnClickListener(view12 -> {
            startActivity(new Intent(this.getActivity(), OutdoorActivity.class));

        });


        btn_cart = view.findViewById(R.id.btn_cart);

        btn_cart.setOnClickListener(view1 -> {
            startActivity(new Intent(this.getActivity(),CartListActivity.class));
        });

        mRecyclerView = view.findViewById(R.id.mostpop_plant_rv);
        mAdapter = new PlantRecyclerViewAdapter(plantList,this.getContext());




       //mRecyclerView.setAdapter(mAdapter);
       //mRecyclerView.suppressLayout(true);

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

                                    load = storageRef.child("PlantProfileImg").child(plant.getPlant_profile_img());

                                    load.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Got the download URL for 'users/me/profile.png'
                                            // Pass it to Picasso to download, show in ImageView and caching
                                            plant.setUri(uri.toString());
                                            mAdapter.notifyDataSetChanged();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                        }
                                    });
                                    plantList.add(plant);


                                }
                            } else {
                                Log.d("suuu", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }


    }

    @Override
    public void onStart() {
        super.onStart();

    }
}