package com.example.mymovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymovie.R;
import com.example.mymovie.model.MovieResponse;
import com.example.mymovie.server.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>{

    Context context;
    List<Integer> favorites;
    private MovieResponse movie;


    public FavoriteAdapter(Context context, List<Integer> favorites) {
        this.context = context;
        this.favorites = favorites;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //set up for get api
        Retrofit get_retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService api = get_retrofit.create(RetrofitService.class);

        Call<MovieResponse> call = api.getMovieByMovieId(favorites.get(position), "dd104acf25822bb6442481f4cde05a64");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movie = response.body();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        holder.textView.setText(movie.getOriginal_title());

        Glide.with(context)
                .load(movie.getBackdrop_path())
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.favorite_name);
            imageView = itemView.findViewById(R.id.item_favorite_img);
        }
    }


}