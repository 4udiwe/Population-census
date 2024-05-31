package com.example.populationcensus;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ReplaceFragment(new ListFragment());

        navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setOnNavigationItemReselectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.list){
                ReplaceFragment(new ListFragment());
            } else if (menuItem.getItemId() == R.id.profile) {
                ReplaceFragment(new ProfileFragment());
            }
        });
    }

    private void ReplaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, null).commit();
    }
}