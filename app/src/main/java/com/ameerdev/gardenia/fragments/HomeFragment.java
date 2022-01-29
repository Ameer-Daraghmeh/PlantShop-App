package com.ameerdev.gardenia.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ameerdev.gardenia.R;
import com.ameerdev.gardenia.adapter.MyPlantsAdapter;
import com.ameerdev.gardenia.adapter.PlantRecyclerViewAdapter;
import com.ameerdev.gardenia.models.Plant;
import com.ameerdev.gardenia.ui.CartListActivity;
import com.ameerdev.gardenia.ui.IndoorActivity;
import com.ameerdev.gardenia.ui.OutdoorActivity;
import com.ameerdev.gardenia.ui.SearchActivity;
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


public class HomeFragment extends Fragment {


    private static final ArrayList<Plant> plantList= new ArrayList<Plant>();
    private RecyclerView mRecyclerView;
    ImageButton btn_search;
    static FirebaseStorage storage = FirebaseStorage.getInstance();
    static StorageReference storageRef = storage.getReference();
    Button btn_cart;
    StorageReference load;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText et_search;
    static  int count = 0;
    private PlantRecyclerViewAdapter mAdapter;
    ImageView iv_indoor;
    ImageView iv_outdoor;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mAdapter = new PlantRecyclerViewAdapter(plantList,this.getContext());
        getPlantList();
        mRecyclerView = view.findViewById(R.id.mostpop_plant_rv);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        //Implementing Search
        btn_search = view.findViewById(R.id.btn_search);
        et_search = view.findViewById(R.id.et_search);
        btn_search.setOnClickListener(view14 -> {

            String tostmsg="Not found";
            String plantName = et_search.getText().toString().toLowerCase();
           for (int i=0 ; i <plantList.size() ; i++){
               if (plantName.contains(plantList.get(i).getName().toLowerCase())){
                   GardeniaApi.getInstance().setSearchPlant(plantList.get(i));
                   startActivity(new Intent(getContext(),SearchActivity.class));
                   tostmsg=null;
               }
           }
            Toast.makeText(this.getContext(), tostmsg, Toast.LENGTH_SHORT).show();
        });

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

        /**
         * Cart Button click
         */
        btn_cart = view.findViewById(R.id.btn_cart);

        btn_cart.setOnClickListener(view1 -> {
            startActivity(new Intent(this.getActivity(),CartListActivity.class));
        });


        // Inflate the layout for this fragment
        return view;
    }

    //get all plants and store them in plantList
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
                                    Plant plant = plants.toObject(Plant.class);
                                    load = storageRef.child("PlantProfileImg").child(plant.getPlant_profile_img());
                                    load.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Got the download URL for 'users/me/profile.png'
                                            plant.setUri(uri.toString());
                                            plantList.add(plant);
                                            mAdapter.notifyDataSetChanged();
                                        }
                                    });
                                }

                            } else {
                                Log.d("suuu", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
    }

}