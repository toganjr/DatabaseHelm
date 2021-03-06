package com.example.databasehelm;


import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    int idStatus;
    FragmentManager manager;
    FragmentTransaction transaction;

    public static Activity MainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity = this;
        idStatus = getIntent().getIntExtra("STATUS",0);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);

        manager = ((AppCompatActivity) this).getSupportFragmentManager();
        transaction = manager.beginTransaction();
        switch(idStatus) {
            case 0:
                getFragmentPage(new ListFragment());
                bottomNavigation.setSelectedItemId(R.id.list);
                break;
            case 1:
                getFragmentPage(new HistoryFragment());
                bottomNavigation.setSelectedItemId(R.id.history);
                break;
            case 2:
                getFragmentPage(new HasilFragment());
                bottomNavigation.setSelectedItemId(R.id.hasil);
                break;
            default:
        }

        //Menampilkan halaman Fragment yang pertama kali muncul


        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                //Menantukan halaman Fragment yang akan tampil
                switch (item.getItemId()){
                    case R.id.history:
                        getFragmentPage(new HistoryFragment());
                        break;

                    case R.id.list:
                        getFragmentPage(new ListFragment());
                        break;

                    case R.id.hasil:
                        getFragmentPage(new HasilFragment());
                        break;
                }
                return true;
            }
        });
    }

    //Menampilkan halaman Fragment
    private boolean getFragmentPage(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
