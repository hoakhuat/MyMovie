package com.example.mymovie.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyHelper extends SQLiteOpenHelper {

    public MyHelper(Context context, String databaseName){
        super(context, databaseName,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createFavoriteTable = "CREATE TABLE FAVORITE("
                + "username text,"
                + "movieId int," +
                "PRIMARY KEY(username, movieId) )";


        db.execSQL(createFavoriteTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
