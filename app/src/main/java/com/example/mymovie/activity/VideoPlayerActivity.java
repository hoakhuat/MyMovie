package com.example.mymovie.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.example.mymovie.R;
import com.example.mymovie.adapter.TrailerAdapter;
import com.example.mymovie.model.trailer.Trailer;
import com.example.mymovie.model.trailer.TrailerResponse;
import com.example.mymovie.server.RetrofitService;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideoPlayerActivity extends AppCompatActivity {
    private int movie_id;
    private String api_key = "dd104acf25822bb6442481f4cde05a64";
    private RecyclerView recyclerViewCast;
    private RetrofitService api;
    private RecyclerView recyclerView;
    private String video_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        Intent intent = getIntent();
        movie_id = intent.getExtras().getInt("movie_id");
        video_id = intent.getExtras().getString("video_id");
        String title = intent.getExtras().getString("title");
        //set name for action bar
//        getSupportActionBar().setTitle(title);
        recyclerView = findViewById(R.id.rv_trailer_more);

        //set up for get api
        Retrofit get_retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = get_retrofit.create(RetrofitService.class);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int visibility = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(visibility);

        getSupportActionBar().hide();


        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(video_id, 0);
            }
        });

        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
                VideoPlayerActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

            @Override
            public void onYouTubePlayerExitFullScreen() {
                VideoPlayerActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });

        loadVideoMovie();

    }

    private void loadVideoMovie() {
        Call<TrailerResponse> call = api.getMovieTrailer(movie_id, api_key);
        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                List<Trailer> trailers = response.body().getResults();
                trailers.remove(trailers.get(0));
                if (trailers.size() > 0) {
                    TrailerAdapter adapter = new TrailerAdapter(VideoPlayerActivity.this, trailers);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(VideoPlayerActivity.this, 1));
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {

            }
        });
    }
}
