package com.ameerdev.gardenia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import com.ameerdev.gardenia.fragments.DiagnoseFragment;
import com.ameerdev.gardenia.fragments.GardenFragment;
import com.ameerdev.gardenia.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;



public class MainActivity extends AppCompatActivity{



    private BottomNavigationView bottomNavigationView;

    private FirebaseAuth mAuth;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final HomeFragment homeFragment = new HomeFragment();
    private static final GardenFragment gardenFragment = new GardenFragment();
    private static final DiagnoseFragment diagnoseFragment = new DiagnoseFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("statt","onCreateMain");
          mAuth = FirebaseAuth.getInstance();
          FirebaseUser user = mAuth.getCurrentUser();
          assert user!=null;



        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                navItemSelected(item);
                return false;
            }
        });


    }







//Nav bar for fragments
    public void navItemSelected(@NonNull MenuItem item) {


        switch(item.getItemId()){
            case R.id.homeFragment:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.fragmentContainer,homeFragment).commit();
                        bottomNavigationView.getMenu().findItem(R.id.homeFragment).setChecked(true);
                        break;

            case R.id.gardenFragment:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.fragmentContainer,gardenFragment).commit();
                bottomNavigationView.getMenu().findItem(R.id.gardenFragment).setChecked(true);
                        break;
            case R.id.diagnoseFragment:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.fragmentContainer,diagnoseFragment).commit();
                bottomNavigationView.getMenu().findItem(R.id.diagnoseFragment).setChecked(true);
                        break;
                }


    }
















}






