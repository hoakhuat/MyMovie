package com.example.mymovie.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymovie.R;
import com.example.mymovie.activity.MovieDetailActivity;
import com.example.mymovie.model.Movie;
import com.example.mymovie.model.MovieResponse;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    Context context ;
    List<MovieResponse> mData;

    public MovieAdapter(Context context, List<MovieResponse> mData) {
        this.context = context;
        this.mData = mData;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(mData.get(position).getOriginal_title());
        Glide.with(context)
                .load(mData.get(position).getPoster_path())
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .into(holder.image);
//        holder.image.setImageResource(mData.get(position).getBackdrop_path());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView image;

        public MyViewHolder(View itemView) {

            super(itemView);
            title = itemView.findViewById(R.id.item_movie_title);
            image = itemView.findViewById(R.id.item_movie_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //listener.onMovieClick(mData.get(getAdapterPosition()),image);
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        MovieResponse cliclekMovie = mData.get(pos);
                        Intent intent = new Intent(context, MovieDetailActivity.class);
                        // send movie information to deatilActivity
                        intent.putExtra("title",mData.get(pos).getOriginal_title());
                        intent.putExtra("poster_path",mData.get(pos).getPoster_path());
                        intent.putExtra("overview",mData.get(pos).getOverview());
                        intent.putExtra("vote_average",mData.get(pos).getVote_average());
                        context.startActivity(intent);
                    }
                }
            });

        }
    }

}
