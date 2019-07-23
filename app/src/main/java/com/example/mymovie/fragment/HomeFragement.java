package com.example.mymovie.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.mymovie.R;
import com.example.mymovie.adapter.MovieAdapter;
import com.example.mymovie.adapter.SliderPagerAdapter;
import com.example.mymovie.model.MovieResponse;
import com.example.mymovie.model.MoviesResponse;
import com.example.mymovie.server.RetrofitService;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragement extends Fragment {
    private List<MovieResponse> slide_movies;
    private ViewPager slider;
    private TabLayout indicator;
    private View view;
    private RecyclerView recyclerViewMovie;
    private RecyclerView recyclerViewMovie2;
    private String api_key = "dd104acf25822bb6442481f4cde05a64";
    private List<MovieResponse> popular;
    private List<MovieResponse> top_rate;
    private RetrofitService api;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_fragment, container, false);
        //find id
        slider = view.findViewById(R.id.viewPager);
        indicator = view.findViewById(R.id.indicator);
        recyclerViewMovie = view.findViewById(R.id.rv_movie);
        top_rate = new ArrayList<>();
        popular = new ArrayList<>();
        slide_movies = new ArrayList<>();

        //set up for get api
        Retrofit get_retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = get_retrofit.create(RetrofitService.class);

        // setup timer
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new HomeFragement.SliderTimer(), 1000, 6000);
        indicator.setupWithViewPager(slider, true);

        loadRecyclerView();
        loadToSlider();

        return view;
    }

    //load slide from api
    private void loadToSlider(){
        Call<MoviesResponse> call = api.getUpComingMovies(api_key);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                List<MovieResponse> movies = response.body().getResults();
                for (int i = 0; i < 4; i++) {
                    slide_movies.add(movies.get(i));
                }
                SliderPagerAdapter adapter = new SliderPagerAdapter(getActivity(), slide_movies);
                slider.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });

    }

    //load data from api
    private void loadRecyclerView() {

        Call<MoviesResponse> call = api.getPopularMovies(api_key);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                List<MovieResponse> movies = response.body().getResults();
                for (int i = 0; i < 8; i++) {
                    popular.add(movies.get(i));
                }
                fillRecyclerView(recyclerViewMovie, popular);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });

        recyclerViewMovie2 = view.findViewById(R.id.rv_movie2);
        Call<MoviesResponse> call2 = api.getTopRatedMovies(api_key);
        call2.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                List<MovieResponse> movies = response.body().getResults();

                for (int i = 0; i < 8; i++) {
                    top_rate.add(movies.get(i));
                }
                fillRecyclerView(recyclerViewMovie2, top_rate);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fillRecyclerView(RecyclerView recyclerView, List<MovieResponse> list) {
        MovieAdapter movieAdapter = new MovieAdapter(getContext(), list);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    }


    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            if (isVisible()) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (slider.getCurrentItem() < slide_movies.size() - 1) {
                            slider.setCurrentItem(slider.getCurrentItem() + 1);
                        } else
                            slider.setCurrentItem(0);
                    }
                });
            }
        }
    }

}
