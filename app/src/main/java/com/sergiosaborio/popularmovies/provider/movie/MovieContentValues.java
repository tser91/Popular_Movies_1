package com.sergiosaborio.popularmovies.provider.movie;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sergiosaborio.popularmovies.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code movie} table.
 */
public class MovieContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return MovieColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable MovieSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable MovieSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public MovieContentValues putTitle(@Nullable String value) {
        mContentValues.put(MovieColumns.TITLE, value);
        return this;
    }

    public MovieContentValues putTitleNull() {
        mContentValues.putNull(MovieColumns.TITLE);
        return this;
    }

    public MovieContentValues putDescription(@Nullable String value) {
        mContentValues.put(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public MovieContentValues putDescriptionNull() {
        mContentValues.putNull(MovieColumns.DESCRIPTION);
        return this;
    }

    public MovieContentValues putReleasedate(@Nullable String value) {
        mContentValues.put(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public MovieContentValues putReleasedateNull() {
        mContentValues.putNull(MovieColumns.RELEASEDATE);
        return this;
    }

    public MovieContentValues putRating(@Nullable String value) {
        mContentValues.put(MovieColumns.RATING, value);
        return this;
    }

    public MovieContentValues putRatingNull() {
        mContentValues.putNull(MovieColumns.RATING);
        return this;
    }

    public MovieContentValues putImage(@Nullable byte[] value) {
        mContentValues.put(MovieColumns.IMAGE, value);
        return this;
    }

    public MovieContentValues putImageNull() {
        mContentValues.putNull(MovieColumns.IMAGE);
        return this;
    }
}
