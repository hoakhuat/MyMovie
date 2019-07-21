package com.example.mymovie.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mymovie.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView MovieThumbnailImg,MovieCoverImg;
    private TextView tv_title,tv_description;
    private FloatingActionButton play_fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        iniview();
    }

    private void iniview() {
//        play_fab = findViewById(R.id.play_fab);
        String movieTitle = getIntent().getExtras().getString("title");
        String poster_path = getIntent().getExtras().getString("poster_path");
        String backdrop_path = getIntent().getExtras().getString("backdrop_path");
        String overview = getIntent().getExtras().getString("overview");

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

        getSupportActionBar().setTitle(movieTitle);
        tv_description = findViewById(R.id.detail_movie_desc);
        tv_description.setText(overview);
        // setup animation
        MovieCoverImg.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_animation));

//        play_fab.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_animation));
    }
}
