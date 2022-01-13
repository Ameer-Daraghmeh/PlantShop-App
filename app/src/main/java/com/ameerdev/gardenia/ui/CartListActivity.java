package com.ameerdev.gardenia.ui;

import static java.lang.String.*;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ameerdev.gardenia.R;
import com.ameerdev.gardenia.adapter.CartListAdapter;
import com.ameerdev.gardenia.models.Plant;

import java.util.ArrayList;

import util.GardeniaApi;

public class CartListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CartListAdapter mAdapter;
    TextView tv_no_cart_item_found;
    GardeniaApi gardeniaApi = GardeniaApi.getInstance();

   TextView tv_subtotal,tv_shipping_charge,tv_total ;
     int  subtotal = 0;
     int shipping = 10;
     int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        tv_no_cart_item_found = findViewById(R.id.tv_no_cart_item_found);

        tv_subtotal = findViewById(R.id.tv_sub_total);
        tv_shipping_charge = findViewById(R.id.tv_shipping_charge);
        tv_total = findViewById(R.id.tv_total_amount);

        ArrayList<Plant>cartList = new ArrayList<>();
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

        mRecyclerView = findViewById(R.id.rv_cart_items_list);


        // gardeniaApi.showcartlist();

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