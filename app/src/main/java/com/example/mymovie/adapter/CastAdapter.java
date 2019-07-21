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

import com.example.mymovie.model.movie_cast.Cast;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.MyViewHolderCast> {
    Context context ;
    List<Cast> mData;

    public CastAdapter(Context context, List<Cast> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolderCast onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MyViewHolderCast(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderCast holder, int position) {
        holder.title.setText(mData.get(position).getName());
        Glide.with(context)
                .load(mData.get(position).getProfile_path())
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolderCast extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView image;

        public MyViewHolderCast(View itemView) {

            super(itemView);
            title = itemView.findViewById(R.id.item_movie_title);
            image = itemView.findViewById(R.id.item_movie_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }
}
