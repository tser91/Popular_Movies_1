package com.sergiosaborio.popularmovies.provider.movie;

import android.net.Uri;
import android.provider.BaseColumns;

import com.sergiosaborio.popularmovies.provider.DBProvider;
import com.sergiosaborio.popularmovies.provider.movie.MovieColumns;
import com.sergiosaborio.popularmovies.provider.review.ReviewColumns;
import com.sergiosaborio.popularmovies.provider.trailer.TrailerColumns;

/**
 * Columns for the {@code movie} table.
 */
public class MovieColumns implements BaseColumns {
    public static final String TABLE_NAME = "movie";
    public static final Uri CONTENT_URI = Uri.parse(DBProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String TITLE = "title";

    public static final String DESCRIPTION = "description";

    public static final String RELEASEDATE = "releaseDate";

    public static final String RATING = "rating";

    public static final String IMAGE = "image";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            TITLE,
            DESCRIPTION,
            RELEASEDATE,
            RATING,
            IMAGE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(TITLE) || c.contains("." + TITLE)) return true;
            if (c.equals(DESCRIPTION) || c.contains("." + DESCRIPTION)) return true;
            if (c.equals(RELEASEDATE) || c.contains("." + RELEASEDATE)) return true;
            if (c.equals(RATING) || c.contains("." + RATING)) return true;
            if (c.equals(IMAGE) || c.contains("." + IMAGE)) return true;
        }
        return false;
    }

}
