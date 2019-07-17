package com.example.mymovie.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.mymovie.R;
import com.example.mymovie.adapter.MovieAdapter;
import com.example.mymovie.adapter.MovieItemClickListener;
import com.example.mymovie.adapter.SliderPagerAdapter;
import com.example.mymovie.model.Movie;
import com.example.mymovie.model.Slide;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragement extends Fragment implements MovieItemClickListener {
    private List<Slide> slides;
    private ViewPager slider;
    private TabLayout indicator;
    private View view;
    private RecyclerView recyclerViewMovie ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_fragment, container, false);
        //find id
        slider = view.findViewById(R.id.viewPager);
        indicator = view.findViewById(R.id.indicator);
        recyclerViewMovie = view.findViewById(R.id.rv_movie);


        //load image to slider
        slides = new ArrayList<>();
        slides.add(new Slide(R.drawable.slide1, "Slide Title \nmore text here"));
        slides.add(new Slide(R.drawable.slide2, "Slide Title \nmore text here"));
        slides.add(new Slide(R.drawable.slide1, "Slide Title \nmore text here"));
        slides.add(new Slide(R.drawable.slide2, "Slide Title \nmore text here"));

        SliderPagerAdapter adapter = new SliderPagerAdapter(getActivity(), slides);
        slider.setAdapter(adapter);

        // setup timer
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new HomeFragement.SliderTimer(),1000,6000);
        indicator.setupWithViewPager(slider,true);

        // Recyclerview Setup
        // ini data

        List<Movie> lstMovies = new ArrayList<>();
        lstMovies.add(new Movie("Moana",R.drawable.moana,R.drawable.spidercover));
        lstMovies.add(new Movie("Black P",R.drawable.blackp,R.drawable.spidercover));
        lstMovies.add(new Movie("The Martian",R.drawable.themartian));
        lstMovies.add(new Movie("The Martian",R.drawable.themartian));
        lstMovies.add(new Movie("The Martian",R.drawable.themartian));
        lstMovies.add(new Movie("The Martian",R.drawable.themartian));

        MovieAdapter movieAdapter = new MovieAdapter(getContext(),lstMovies, this);
        recyclerViewMovie.setAdapter(movieAdapter);
        recyclerViewMovie.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        return view;
    }

    @Override
    public void onMovieClick(Movie movie, ImageView movieImageView) {

    }

    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            if (isVisible()) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (slider.getCurrentItem() < slides.size() - 1) {
                            slider.setCurrentItem(slider.getCurrentItem() + 1);
                        } else
                            slider.setCurrentItem(0);
                    }
                });
            }
        }
    }

}