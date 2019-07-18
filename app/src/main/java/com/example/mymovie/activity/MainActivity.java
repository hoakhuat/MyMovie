package com.example.mymovie.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mymovie.R;
import com.example.mymovie.model.Slide;
import com.example.mymovie.fragment.AccountFragment;
import com.example.mymovie.fragment.FavouriteFragment;
import com.example.mymovie.fragment.HomeFragement;
import com.example.mymovie.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView navigationView;
    private List<Slide> slides ;
    private ViewPager slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find id
        navigationView = findViewById(R.id.nav_bottom);


        //set listener
        navigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeFragement());
    }

    public boolean loadFragment(Fragment fragment){
        if(fragment!=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_content, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment = null;
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new HomeFragement();
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchFragment();
                    break;
                case R.id.nav_favourite:
                    selectedFragment = new FavouriteFragment();
                    break;
                case R.id.nav_account:
                    selectedFragment = new AccountFragment();
                    break;
            }
        return loadFragment(selectedFragment);
    }

}
