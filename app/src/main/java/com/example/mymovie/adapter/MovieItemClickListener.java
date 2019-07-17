package com.example.mymovie.adapter;

import android.widget.ImageView;

import com.example.mymovie.model.Movie;


public interface MovieItemClickListener {
    // we will need the imageview to make the shared animation between the two activity
    void onMovieClick(Movie movie, ImageView movieImageView);

}
