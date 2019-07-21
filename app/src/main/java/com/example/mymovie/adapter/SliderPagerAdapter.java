package com.example.mymovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.mymovie.R;
import com.example.mymovie.activity.MovieDetailActivity;
import com.example.mymovie.model.MovieResponse;

import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {

    private Context mContext ;
    private List<MovieResponse> mList ;


    public SliderPagerAdapter(Context mContext, List<MovieResponse> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.slide,null);
        ImageView slideImg = slideLayout.findViewById(R.id.slide_img);
        TextView slideText = slideLayout.findViewById(R.id.slide_title);
        slideText.setText(mList.get(position).getOriginal_title());
        Glide.with(mContext)
                .load(mList.get(position).getBackdrop_path())
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .into(slideImg);

        slideImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    MovieResponse cliclekMovie = mList.get(position);
                    Intent intent = new Intent(mContext, MovieDetailActivity.class);
                    // send movie information to deatilActivity
                    intent.putExtra("title",mList.get(position).getOriginal_title());
                    intent.putExtra("poster_path",mList.get(position).getPoster_path());
                    intent.putExtra("overview",mList.get(position).getOverview());
                    intent.putExtra("backdrop_path",mList.get(position).getBackdrop_path());
                    mContext.startActivity(intent);
            }
        });
        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
