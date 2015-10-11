package com.sergiosaborio.popularmovies.provider.movie;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sergiosaborio.popularmovies.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code movie} table.
 */
public class MovieCursor extends AbstractCursor implements MovieModel {
    public MovieCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(MovieColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getTitle() {
        String res = getStringOrNull(MovieColumns.TITLE);
        return res;
    }

    /**
     * Get the {@code description} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getDescription() {
        String res = getStringOrNull(MovieColumns.DESCRIPTION);
        return res;
    }

    /**
     * Get the {@code releasedate} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getReleasedate() {
        String res = getStringOrNull(MovieColumns.RELEASEDATE);
        return res;
    }

    /**
     * Get the {@code rating} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getRating() {
        String res = getStringOrNull(MovieColumns.RATING);
        return res;
    }

    /**
     * Get the {@code image} value.
     * Can be {@code null}.
     */
    @Nullable
    public byte[] getImage() {
        byte[] res = getBlobOrNull(MovieColumns.IMAGE);
        return res;
    }
}
