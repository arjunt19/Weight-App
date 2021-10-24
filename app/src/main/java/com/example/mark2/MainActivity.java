package com.example.mark2;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,  new fragment_home()).commit();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =  new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch(item.getItemId()){
                case R.id.menu_home:
                    selectedFragment = new fragment_home();
                    break;
                case R.id.menu_log:
                    selectedFragment = new fragment_log();
                    break;
                case R.id.menu_statistics:
                    selectedFragment = new fragment_stats();
                     break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,  selectedFragment).commit();

            return true;
        }
    };
}