package com.sergiosaborio.popularmovies.provider.trailer;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sergiosaborio.popularmovies.provider.base.AbstractCursor;
import com.sergiosaborio.popularmovies.provider.movie.*;

/**
 * Cursor wrapper for the {@code trailer} table.
 */
public class TrailerCursor extends AbstractCursor implements TrailerModel {
    public TrailerCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(TrailerColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getName() {
        String res = getStringOrNull(TrailerColumns.NAME);
        return res;
    }

    /**
     * Get the {@code url} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getUrl() {
        String res = getStringOrNull(TrailerColumns.URL);
        return res;
    }

    /**
     * Get the {@code movie_id} value.
     */
    public long getMovieId() {
        Long res = getLongOrNull(TrailerColumns.MOVIE_ID);
        if (res == null)
            throw new NullPointerException("The value of 'movie_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMovieTitle() {
        String res = getStringOrNull(MovieColumns.TITLE);
        return res;
    }

    /**
     * Get the {@code description} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMovieDescription() {
        String res = getStringOrNull(MovieColumns.DESCRIPTION);
        return res;
    }

    /**
     * Get the {@code releasedate} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMovieReleasedate() {
        String res = getStringOrNull(MovieColumns.RELEASEDATE);
        return res;
    }

    /**
     * Get the {@code rating} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMovieRating() {
        String res = getStringOrNull(MovieColumns.RATING);
        return res;
    }

    /**
     * Get the {@code image} value.
     * Can be {@code null}.
     */
    @Nullable
    public byte[] getMovieImage() {
        byte[] res = getBlobOrNull(MovieColumns.IMAGE);
        return res;
    }
}
