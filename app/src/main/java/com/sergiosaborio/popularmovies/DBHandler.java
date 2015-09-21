package com.sergiosaborio.popularmovies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SergioSaborio on 9/20/15.
 */
public class DBHandler extends SQLiteOpenHelper {
    public DBHandler(Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.MovieTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.TrailerTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.ReviewTable.CREATE_TABLE);
    }

    // Method is called during an upgrade of the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.MovieTable.DELETE_TABLE);
        db.execSQL(DatabaseContract.TrailerTable.DELETE_TABLE);
        db.execSQL(DatabaseContract.ReviewTable.DELETE_TABLE);
        onCreate(db);
    }
}