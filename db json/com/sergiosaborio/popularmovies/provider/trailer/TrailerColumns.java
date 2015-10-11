package com.sergiosaborio.popularmovies.provider.trailer;

import android.net.Uri;
import android.provider.BaseColumns;

import com.sergiosaborio.popularmovies.provider.DBProvider;
import com.sergiosaborio.popularmovies.provider.movie.MovieColumns;
import com.sergiosaborio.popularmovies.provider.review.ReviewColumns;
import com.sergiosaborio.popularmovies.provider.trailer.TrailerColumns;

/**
 * Columns for the {@code trailer} table.
 */
public class TrailerColumns implements BaseColumns {
    public static final String TABLE_NAME = "trailer";
    public static final Uri CONTENT_URI = Uri.parse(DBProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String NAME = "name";

    public static final String URL = "url";

    public static final String MOVIE_ID = "movie_id";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            NAME,
            URL,
            MOVIE_ID
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
            if (c.equals(URL) || c.contains("." + URL)) return true;
            if (c.equals(MOVIE_ID) || c.contains("." + MOVIE_ID)) return true;
        }
        return false;
    }

    public static final String PREFIX_MOVIE = TABLE_NAME + "__" + MovieColumns.TABLE_NAME;
}
