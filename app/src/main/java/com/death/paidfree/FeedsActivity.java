package com.death.paidfree;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class FeedsActivity extends AppCompatActivity {

    private BottomNavigationViewEx bottomNavigationViewEx;
    Fragment fragment;
    FragmentTransaction fragmentTransaction, fTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);
        bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottom_nav);
        bottomNavigationViewEx.setCurrentItem(0);

        Fragment defFragment = new DealsFragment();
        fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.container, defFragment).commit();


        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.deals:
                        fragment = new DealsFragment();
                        break;
                    case R.id.feeds:
                        fragment = new FeedsFragment();
                        break;
                    case R.id.interesting:
                        fragment = new InterestingFragment();
                        break;
                }
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment).commit();
                return true;
            }
        });

    }
}
