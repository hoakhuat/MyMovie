package com.example.mymovie.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovie.R;
import com.example.mymovie.activity.MovieDetailActivity;
import com.example.mymovie.adapter.FavoriteAdapter;
import com.example.mymovie.adapter.MovieAdapter;
import com.example.mymovie.model.MovieResponse;
import com.example.mymovie.sqlite.MyHelper;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {

    private  String username;
    private List<Integer> favorites;
    MyHelper myHelper;
    SQLiteDatabase db;

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite,null);
        recyclerView = view.findViewById(R.id.rv_favorite);


        //get username from sharepre
        username = "hoakhuat";

        loadFavorite();

        fillRecyclerView(recyclerView, favorites );


        return view;
    }


    private void loadFavorite(){
        try{
            //get database
            myHelper = new MyHelper(MovieDetailActivity.getContext(), "favorite.db");
            favorites = new ArrayList<>();
            db = myHelper.getReadableDatabase();
            String select = "SELECT * FROM Favorite WHERE username = '"+username+"'";
            Cursor cursor = db.rawQuery(select, null);
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("movieId"));
                favorites.add(id);
            }
        }catch (Exception e){
            favorites = new ArrayList<>();
        }

    }


    private void fillRecyclerView(RecyclerView recyclerView, List<Integer> list) {
        FavoriteAdapter favoriteAdapter = new FavoriteAdapter(getContext(), list);
        recyclerView.setAdapter(favoriteAdapter);
    }

}
