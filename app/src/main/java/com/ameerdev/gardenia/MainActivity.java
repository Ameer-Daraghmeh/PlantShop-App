package com.ameerdev.gardenia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity{



    BottomNavigationView bottomNavigationView;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new HomeFragment()).commit();

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
                                replace(R.id.fragmentContainer,new HomeFragment()).commit();
                        bottomNavigationView.getMenu().findItem(R.id.homeFragment).setChecked(true);
                        break;

            case R.id.gardenFragment:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.fragmentContainer,new GardenFragment()).commit();
                bottomNavigationView.getMenu().findItem(R.id.gardenFragment).setChecked(true);
                        break;
            case R.id.diagnoseFragment:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.fragmentContainer,new DiagnoseFragment()).commit();
                bottomNavigationView.getMenu().findItem(R.id.diagnoseFragment).setChecked(true);
                        break;
                }
    }
















}






