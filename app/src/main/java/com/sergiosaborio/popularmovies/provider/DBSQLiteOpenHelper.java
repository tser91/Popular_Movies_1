package com.sergiosaborio.popularmovies.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.sergiosaborio.popularmovies.BuildConfig;
import com.sergiosaborio.popularmovies.provider.movie.MovieColumns;
import com.sergiosaborio.popularmovies.provider.review.ReviewColumns;
import com.sergiosaborio.popularmovies.provider.trailer.TrailerColumns;

public class DBSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = DBSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;
    private static DBSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final DBSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_MOVIE = "CREATE TABLE IF NOT EXISTS "
            + MovieColumns.TABLE_NAME + " ( "
            + MovieColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MovieColumns.TITLE + " TEXT DEFAULT 'Movie name not available', "
            + MovieColumns.DESCRIPTION + " TEXT DEFAULT 'Movie plot not available', "
            + MovieColumns.RELEASEDATE + " TEXT DEFAULT 'Movie release date not available', "
            + MovieColumns.RATING + " TEXT DEFAULT 'Movie release date not available', "
            + MovieColumns.IMAGE + " BLOB "
            + " );";

    public static final String SQL_CREATE_TABLE_REVIEW = "CREATE TABLE IF NOT EXISTS "
            + ReviewColumns.TABLE_NAME + " ( "
            + ReviewColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ReviewColumns.NAME + " TEXT DEFAULT 'Reviewer not available', "
            + ReviewColumns.REVIEW + " TEXT, "
            + ReviewColumns.MOVIE_ID + " INTEGER NOT NULL "
            + ", CONSTRAINT fk_movie_id FOREIGN KEY (" + ReviewColumns.MOVIE_ID + ") REFERENCES movie (_id) ON DELETE CASCADE"
            + " );";

    public static final String SQL_CREATE_TABLE_TRAILER = "CREATE TABLE IF NOT EXISTS "
            + TrailerColumns.TABLE_NAME + " ( "
            + TrailerColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TrailerColumns.NAME + " TEXT DEFAULT '“Trailer”', "
            + TrailerColumns.URL + " TEXT, "
            + TrailerColumns.MOVIE_ID + " INTEGER NOT NULL "
            + ", CONSTRAINT fk_movie_id FOREIGN KEY (" + TrailerColumns.MOVIE_ID + ") REFERENCES movie (_id) ON DELETE CASCADE"
            + " );";

    // @formatter:on

    public static DBSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static DBSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static DBSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new DBSQLiteOpenHelper(context);
    }

    private DBSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new DBSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static DBSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new DBSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private DBSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new DBSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_REVIEW);
        db.execSQL(SQL_CREATE_TABLE_TRAILER);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
