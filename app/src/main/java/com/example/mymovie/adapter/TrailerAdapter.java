package com.example.mymovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codewaves.youtubethumbnailview.ThumbnailLoader;
import com.codewaves.youtubethumbnailview.ThumbnailView;
import com.example.mymovie.R;
import com.example.mymovie.activity.MovieDetailActivity;
import com.example.mymovie.activity.VideoPlayerActivity;
import com.example.mymovie.model.trailer.Trailer;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {
    Context context;
    List<Trailer> mData;
    int movie_id;

    public TrailerAdapter(Context context, List<Trailer> mData, int movie_id) {
        this.context = context;
        this.mData = mData;
        this.movie_id = movie_id;
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
//        YouTubePlayerTracker tracker = new YouTubePlayerTracker();
//        holder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(YouTubePlayer youTubePlayer) {
//                youTubePlayer.cueVideo(mData.get(position).getKey(),0);
//            }
//        });

        ThumbnailLoader.initialize("AIzaSyBepF_k1AtJsnmzFO6GNxnsy0wQj3LikpQ");
        String url = "https://www.youtube.com/watch?v=" + mData.get(position).getKey();
        holder.youTubePlayerView.loadThumbnail(url);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{

//        private YouTubePlayerView youTubePlayerView;
        private TextView name;
        private ThumbnailView youTubePlayerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            youTubePlayerView = itemView.findViewById(R.id.youtube_trailer);
            name = itemView.findViewById(R.id.trailer_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, VideoPlayerActivity.class);
                        intent.putExtra("movie_id", movie_id);
                        intent.putExtra("video_id", mData.get(pos).getKey());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
