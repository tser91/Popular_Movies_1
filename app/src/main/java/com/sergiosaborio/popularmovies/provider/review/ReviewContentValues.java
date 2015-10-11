package com.sergiosaborio.popularmovies.provider.review;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sergiosaborio.popularmovies.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code review} table.
 */
public class ReviewContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ReviewColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable ReviewSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable ReviewSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public ReviewContentValues putName(@Nullable String value) {
        mContentValues.put(ReviewColumns.NAME, value);
        return this;
    }

    public ReviewContentValues putNameNull() {
        mContentValues.putNull(ReviewColumns.NAME);
        return this;
    }

    public ReviewContentValues putReview(@Nullable String value) {
        mContentValues.put(ReviewColumns.REVIEW, value);
        return this;
    }

    public ReviewContentValues putReviewNull() {
        mContentValues.putNull(ReviewColumns.REVIEW);
        return this;
    }

    public ReviewContentValues putMovieId(long value) {
        mContentValues.put(ReviewColumns.MOVIE_ID, value);
        return this;
    }

}
