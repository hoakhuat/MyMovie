package com.example.mymovie.adapter;

import android.app.Activity;
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
import com.example.mymovie.model.trailer.Trailer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {
    Context context;
    List<Trailer> mData;

    public TrailerAdapter(Context context, List<Trailer> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trailer,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.name.setText(mData.get(position).getName());
        YouTubePlayerTracker tracker = new YouTubePlayerTracker();
        holder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(mData.get(position).getKey(),0);
            }
        });

//        String url = "http://img.youtube.com/vi/"+mData.get(position).getKey()+"/default.jpg";
//        Glide.with(context).load(url).placeholder(R.drawable.ic_back_vector).centerCrop().into(holder.youTubePlayerView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{

        private YouTubePlayerView youTubePlayerView;
        private TextView name;
//        private ImageView youTubePlayerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            youTubePlayerView = itemView.findViewById(R.id.youtube_trailer);
            name = itemView.findViewById(R.id.trailer_name);
        }
    }
}
