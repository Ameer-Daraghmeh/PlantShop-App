package com.ameerdev.gardenia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ameerdev.gardenia.MainActivity;
import com.ameerdev.gardenia.R;
import com.ameerdev.gardenia.RegisterActivity;
import com.ameerdev.gardenia.adapter.CartListAdapter;
import com.ameerdev.gardenia.models.Plant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import util.GardeniaApi;

public class CartListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CartListAdapter mAdapter;
    TextView tv_no_cart_item_found;
    GardeniaApi gardeniaApi = GardeniaApi.getInstance();
    Button btn_checkout;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference;

   TextView tv_subtotal,tv_shipping_charge,tv_total ;
     int  subtotal = 0;
     int shipping = 10;
     int total = 0;

    static ArrayList<Plant>cartList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);


        tv_no_cart_item_found = findViewById(R.id.tv_no_cart_item_found);
        btn_checkout = findViewById(R.id.btn_checkout);
        tv_subtotal = findViewById(R.id.tv_sub_total);
        tv_shipping_charge = findViewById(R.id.tv_shipping_charge);
        tv_total = findViewById(R.id.tv_total_amount);

        cartList = gardeniaApi.getCartList();

        try{
            for (int i=0 ; i<cartList.size();i++){
                int price =  Integer.parseInt(cartList.get(i).getPrice2());
                subtotal+=price;

            }
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
        total = subtotal+shipping;

        String s1= "$"+subtotal+".00";
        String s2= "$"+shipping+".00";
        String s3= "$"+total+".00";

        tv_subtotal.setText(s1);
        tv_shipping_charge.setText(s2);
        tv_total.setText(s3);

        /**
         * Checkout Buttonnnnnnnnnn
         */
        btn_checkout.setOnClickListener(view -> {

            if(gardeniaApi.getUserId()!=null){

                collectionReference =
                        db.collection("UserPlants");

                Map<String, String> userPlantsObj = new HashMap<>();
                userPlantsObj.put("userId", gardeniaApi.getUserId());

                for (int i=0 ; i<cartList.size();i++) {

                    userPlantsObj.put(cartList.get(i).getId() ,cartList.get(i).getName());
                }
                //save to our Firestore database
                collectionReference.add(userPlantsObj)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                documentReference.get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (Objects.requireNonNull(task.getResult()).exists()) {
                                                    Intent intent = new Intent(CartListActivity.this,
                                                            MainActivity.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                            }
                        });
            }


        });

        mRecyclerView = findViewById(R.id.rv_cart_items_list);

        /**
         * Show Cart Items if exist
         */
        if (gardeniaApi.getCartList().size()>0){
            mAdapter = new CartListAdapter(gardeniaApi.getCartList(), this);

            tv_no_cart_item_found.setVisibility(View.GONE);

            mRecyclerView.setVisibility(View.VISIBLE);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

            mRecyclerView.setAdapter(mAdapter);

            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setHasFixedSize(true);
        }else{

        }
    }




}