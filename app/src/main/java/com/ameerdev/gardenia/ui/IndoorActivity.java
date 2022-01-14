package com.ameerdev.gardenia.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

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

public class IndoorActivity extends AppCompatActivity {

    private static final ArrayList<Plant> plantList= new ArrayList<>();
    private RecyclerView mRecyclerView;
    private  PlantRecyclerViewAdapter mAdapter ;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    static FirebaseStorage storage = FirebaseStorage.getInstance();
    static StorageReference storageRef = storage.getReference();
    static int count = 0;
    StorageReference load;


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

        if (count == 0){
            count++;
            db.collection("Indoor")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot plants : task.getResult()) {

                                    Plant plant = plants.toObject(Plant.class);
                                    //Retrieve Plant Image
                                    load = storageRef.child("PlantProfileImg").child(plant.getPlant_profile_img());
                                    load.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Got the download URL
                                            plant.setUri(uri.toString());
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                        }
                                    });
                                    //Store Plant object and view in adapter
                                    plantList.add(plant);
                                    mAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Log.d("suuu", "Error getting documents.", task.getException());
                            }
                        }
                    });
}

}

}