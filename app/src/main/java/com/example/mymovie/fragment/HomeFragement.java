package com.example.mymovie.fragment;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.mymovie.BuildConfig;
import com.example.mymovie.activity.MovieDetailActivity;
import com.example.mymovie.R;
import com.example.mymovie.adapter.MovieAdapter;
import com.example.mymovie.adapter.MovieItemClickListener;
import com.example.mymovie.adapter.SliderPagerAdapter;
import com.example.mymovie.client.RetrofitClient;
import com.example.mymovie.model.Movie;
import com.example.mymovie.model.MovieResponse;
import com.example.mymovie.model.MoviesResponse;
import com.example.mymovie.model.Slide;
import com.example.mymovie.server.RetrofitService;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class HomeFragement extends Fragment{
    private List<Slide> slides;
    private ViewPager slider;
    private TabLayout indicator;
    private View view;
    private RecyclerView recyclerViewMovie;
    private RetrofitService retrofitService;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;
    private MovieAdapter adapter;
    private List<MovieResponse> movieList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_fragment, container, false);
        //find id
        slider = view.findViewById(R.id.viewPager);
        indicator = view.findViewById(R.id.indicator);
        recyclerViewMovie = view.findViewById(R.id.rv_movie);
//        RetrofitClient client = new RetrofitClient();
//        retrofitService = client.getClient().create(RetrofitService.class);


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
        //set up for get api
        Retrofit get_retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService api = get_retrofit.create(RetrofitService.class);
        Call<MoviesResponse> call = api.getPopularMovies("dd104acf25822bb6442481f4cde05a64");
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                List<MovieResponse> movies = response.body().getResults();
                List<MovieResponse> list = new ArrayList<>();
                for(int i=0;i<8;i++){
                    list.add(movies.get(i));
                }
                MovieAdapter movieAdapter = new MovieAdapter(getContext(),list);
                recyclerViewMovie.setAdapter(movieAdapter);
                recyclerViewMovie.smoothScrollToPosition(0);
                recyclerViewMovie.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });

        return view;
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
