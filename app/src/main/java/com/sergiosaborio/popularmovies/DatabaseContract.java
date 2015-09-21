package com.sergiosaborio.popularmovies;

import android.provider.BaseColumns;

/**
 * Created by SergioSaborio on 9/20/15.
 *
 * Reference: http://stackoverflow.com/questions/17451931/how-to-use-a-contract-class-in-android
 */
public class DatabaseContract {

    public static final  int    DATABASE_VERSION   = 1;
    public static final  String DATABASE_NAME      = "popularMovies.db";
    private static final String TEXT_TYPE          = " TEXT";
    private static final String BLOB_TYPE          = " BLOB";
    private static final String COMMA_SEP          = ",";
    private static final String NOT_NULL           = " NOT NULL";
    private static final String PRIMARY_KEY        = " INTEGER PRIMARY KEY ";
    private static final String FOREIGN_KEY        = " FOREIGN KEY(";
    private static final String MOVIE_TABLE        = "movie";
    private static final String TRAILER_TABLE      = "trailer";
    private static final String REVIEW_TABLE       = "review";
    private static final String TABLE_ID           = "id";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private DatabaseContract() {}

    public static abstract class MovieTable implements BaseColumns {
        public static final String TABLE_NAME       = MOVIE_TABLE;
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String RELEASE_DATE = "releaseDate";
        public static final String IMAGE = "image";
        public static final String RATING = "rating";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + PRIMARY_KEY + NOT_NULL + COMMA_SEP +
                TITLE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                RELEASE_DATE + TEXT_TYPE + COMMA_SEP +
                IMAGE + BLOB_TYPE + COMMA_SEP +
                RATING + TEXT_TYPE + COMMA_SEP + " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class TrailerTable implements BaseColumns {
        public static final String TABLE_NAME       = TRAILER_TABLE;
        public static final String MOVIEDB_ID = "movieDBId";
        public static final String URL = "url";
        public static final String NAME = "name";
        public static final String FOREIGN_KEY_MOVIE_ID = "movie_id";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + PRIMARY_KEY + NOT_NULL + COMMA_SEP +
                MOVIEDB_ID + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                URL + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                NAME + TEXT_TYPE + COMMA_SEP +
                FOREIGN_KEY +FOREIGN_KEY_MOVIE_ID+ ") REFERENCES "+ MOVIE_TABLE+" ("+TABLE_ID+")" +
                " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class ReviewTable implements BaseColumns {
        public static final String TABLE_NAME       = REVIEW_TABLE;
        public static final String REVIEW = "review";
        public static final String NAME = "name";
        public static final String FOREIGN_KEY_MOVIE_ID = "movie_id";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + PRIMARY_KEY + NOT_NULL + COMMA_SEP +
                REVIEW + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                NAME + TEXT_TYPE + COMMA_SEP +
                FOREIGN_KEY +FOREIGN_KEY_MOVIE_ID+ ") REFERENCES "+ MOVIE_TABLE+" ("+TABLE_ID+")" +
                " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}
