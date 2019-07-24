package com.example.mymovie.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mymovie.R;
import com.example.mymovie.fragment.FavouriteFragment;
import com.example.mymovie.fragment.HomeFragement;
import com.example.mymovie.fragment.SearchFragment;
import com.example.mymovie.sqlite.MyHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private Fragment homeFragment;
    private Fragment searchFragment;
    private Fragment favouriteFragment;
    private Fragment accountFragment;
    private Fragment activeFragment;

    private  String username;
    private List<Integer> favorites;
    MyHelper myHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if(username==null){
//            Intent intent = new Intent(this,LoginActivity.class);
//            startActivity(intent);
//        }
//        createDB();

        //find
        navigationView = findViewById(R.id.nav_bottom);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        homeFragment = new HomeFragement();
        searchFragment = new SearchFragment();
        favouriteFragment = new FavouriteFragment();
        accountFragment = new FavouriteFragment();
        activeFragment = homeFragment;

        addFragment(accountFragment,"3");
//        addFragment(favouriteFragment,"3");
        addFragment(searchFragment,"2");

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_content, homeFragment, "1")
                .commit();
    }

    public void addFragment(Fragment fragment, String tag) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_content, fragment, tag)
                    .hide(fragment)
                    .commit();
    }

    public void loadNavigationOnClick(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(activeFragment)
                    .show(fragment)
                    .commit();

            activeFragment = fragment;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    loadNavigationOnClick(homeFragment);
                    return true;
                case R.id.nav_search:
                    loadNavigationOnClick(searchFragment);
                    return true;
//                case R.id.nav_favourite:
//                    favouriteFragment = new FavouriteFragment();
//                    loadNavigationOnClick(favouriteFragment);
//                    return true;
                case R.id.nav_account:
                    loadNavigationOnClick(accountFragment);
                    return true;
            }
            return false;
        }
    };

//    public void createDB(){
//        myHelper = new MyHelper(this, "favorite.db");
//    }

}