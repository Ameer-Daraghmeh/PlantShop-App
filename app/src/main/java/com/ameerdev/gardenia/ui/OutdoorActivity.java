package com.ameerdev.gardenia.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.ameerdev.gardenia.R;
import com.ameerdev.gardenia.adapter.PlantRecyclerViewAdapter;
import com.ameerdev.gardenia.models.Plant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;

public class OutdoorActivity extends AppCompatActivity {

    private static final ArrayList<Plant> plantList= new ArrayList<>();
    private RecyclerView mRecyclerView;
    private PlantRecyclerViewAdapter mAdapter;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    static FirebaseStorage storage = FirebaseStorage.getInstance();
    static StorageReference storageRef = storage.getReference();
    static int count = 0;
    StorageReference load;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor);
        getPlantList();
        mRecyclerView = findViewById(R.id.rv_indoor);
        mAdapter = new PlantRecyclerViewAdapter(plantList,this);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

    }
    void getPlantList(){

        if (count == 0){
            count++;
            db.collection("Plants")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot plants : task.getResult()) {
                                    Log.d("sddd", Objects.requireNonNull(plants.getData().get("type")).toString());
                                    if(Objects.requireNonNull(plants.getData().get("type")).
                                            toString().equals("outdoor"))
                                    {
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
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                // Handle any errors
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