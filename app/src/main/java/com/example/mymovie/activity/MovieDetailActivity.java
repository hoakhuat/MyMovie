package com.example.mymovie.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mymovie.R;
import com.example.mymovie.adapter.CastAdapter;
import com.example.mymovie.adapter.MovieAdapter;
import com.example.mymovie.data.FavoriteDbHelper;
import com.example.mymovie.model.MovieResponse;
import com.example.mymovie.model.MoviesResponse;
import com.example.mymovie.model.movie_cast.Cast;
import com.example.mymovie.model.movie_cast.CastAndCrew;
import com.example.mymovie.model.trailer.Trailer;
import com.example.mymovie.model.trailer.TrailerResponse;
import com.example.mymovie.server.RetrofitService;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView MovieThumbnailImg,MovieCoverImg;
    private TextView tv_title,tv_description;
    private FloatingActionButton play_fab;
    private RecyclerView recyclerViewCast;
    private RetrofitService api;
    private String api_key = "dd104acf25822bb6442481f4cde05a64";
    private int movie_id;
    private String movieTitle;
    private MaterialFavoriteButton favoriteButton;
    private MovieResponse favorite;
    private MovieResponse movie;
    private FavoriteDbHelper favoriteDbHelper;
    private String poster_path;
    private String overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        play_fab = findViewById(R.id.play_fab);
        recyclerViewCast = findViewById(R.id.rv_cast);
        favoriteButton = findViewById(R.id.favorite_button);

        //set up for get api
        Retrofit get_retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = get_retrofit.create(RetrofitService.class);

        iniview();

        loadCast();


    }

    private void iniview() {
        movieTitle = getIntent().getExtras().getString("title");
        poster_path = getIntent().getExtras().getString("poster_path");
        String backdrop_path = getIntent().getExtras().getString("backdrop_path");
        overview = getIntent().getExtras().getString("overview");
        movie_id = getIntent().getExtras().getInt("movie_id");

        //set name for action bar
        getSupportActionBar().setTitle(movieTitle);

        MovieThumbnailImg = findViewById(R.id.detail_movie_img);
        Glide.with(this)
                .load(poster_path)
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .into(MovieThumbnailImg);

        MovieCoverImg = findViewById(R.id.detail_movie_cover);
        Glide.with(this)
                .load(backdrop_path)
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .into(MovieCoverImg);

        tv_title = findViewById(R.id.detail_movie_title);
        tv_title.setText(movieTitle);

        tv_description = findViewById(R.id.detail_movie_desc);
        tv_description.setText(overview);
        // setup animation
        MovieCoverImg.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_animation));

        play_fab.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_animation));

        //click to button play
        play_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadVideoMovie();
            }
        });
    }

    private void loadVideoMovie(){
        Call<TrailerResponse> call = api.getMovieTrailer(movie_id,api_key);
        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                List<Trailer> trailer = response.body().getResults();
                if(trailer.size()>0) {
                    Intent intent = new Intent(MovieDetailActivity.this, VideoPlayerActivity.class);
                    intent.putExtra("movie_id", movie_id);
                    intent.putExtra("video_id", trailer.get(0).getKey());
//                    intent.putExtra("title", movieTitle);
                    startActivity(intent);
                }else {
                    Toast.makeText(MovieDetailActivity.this, "Comming soon", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {

            }
        });
    }

    private void loadCast(){
        Call<CastAndCrew> call = api.getCastsByMovieId(movie_id,api_key);
        call.enqueue(new Callback<CastAndCrew>() {
            @Override
            public void onResponse(Call<CastAndCrew> call, Response<CastAndCrew> response) {
                List<Cast> casts = response.body().getCast();
                List<Cast> list = new ArrayList<>();
                for (int i =0;i<8;i++){
                    list.add(casts.get(i));
                }
                fillRecyclerView(recyclerViewCast,list);
            }

            @Override
            public void onFailure(Call<CastAndCrew> call, Throwable t) {

            }
        });
    }

    private void fillRecyclerView(RecyclerView recyclerView, List<Cast> list) {
        CastAdapter castAdapter = new CastAdapter(this, list);
        recyclerView.setAdapter(castAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }


}
