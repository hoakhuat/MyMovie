package com.example.mymovie.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovie.R;
import com.example.mymovie.adapter.MovieAdapter;
import com.example.mymovie.model.MovieResponse;
import com.example.mymovie.model.MoviesResponse;
import com.example.mymovie.server.RetrofitService;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment implements MaterialSearchBar.OnSearchActionListener{
    private String api_key = "dd104acf25822bb6442481f4cde05a64";
    private List<MovieResponse> popular;
    private RetrofitService api;
    private MaterialSearchBar searchBar;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        searchBar = view.findViewById(R.id.searchBar);
        recyclerView = view.findViewById(R.id.rv_search);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        //set up for get api
        Retrofit get_retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = get_retrofit.create(RetrofitService.class);

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence == null || charSequence.toString().isEmpty()){

                }else {
                    String textSearch = charSequence.toString();
                    Call<MoviesResponse> call = api.getMoviesByQuery(api_key,textSearch);
                    call.enqueue(new Callback<MoviesResponse>() {
                        @Override
                        public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                            List<MovieResponse> list = response.body().getResults();
                            MovieAdapter movieAdapter = new MovieAdapter(getContext(), list);
                            recyclerView.setAdapter(movieAdapter);
//                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                        }

                        @Override
                        public void onFailure(Call<MoviesResponse> call, Throwable t) {

                        }
                    });
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




        return view;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
        String s = enabled ? "enabled" : "disabled";
        Toast.makeText(getContext(), "Search " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }

}
