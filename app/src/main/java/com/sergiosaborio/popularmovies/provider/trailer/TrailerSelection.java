package com.sergiosaborio.popularmovies.provider.trailer;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.sergiosaborio.popularmovies.provider.base.AbstractSelection;
import com.sergiosaborio.popularmovies.provider.movie.*;

/**
 * Selection for the {@code trailer} table.
 */
public class TrailerSelection extends AbstractSelection<TrailerSelection> {
    @Override
    protected Uri baseUri() {
        return TrailerColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code TrailerCursor} object, which is positioned before the first entry, or null.
     */
    public TrailerCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new TrailerCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public TrailerCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code TrailerCursor} object, which is positioned before the first entry, or null.
     */
    public TrailerCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new TrailerCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public TrailerCursor query(Context context) {
        return query(context, null);
    }


    public TrailerSelection id(long... value) {
        addEquals("trailer." + TrailerColumns._ID, toObjectArray(value));
        return this;
    }

    public TrailerSelection idNot(long... value) {
        addNotEquals("trailer." + TrailerColumns._ID, toObjectArray(value));
        return this;
    }

    public TrailerSelection orderById(boolean desc) {
        orderBy("trailer." + TrailerColumns._ID, desc);
        return this;
    }

    public TrailerSelection orderById() {
        return orderById(false);
    }

    public TrailerSelection name(String... value) {
        addEquals(TrailerColumns.NAME, value);
        return this;
    }

    public TrailerSelection nameNot(String... value) {
        addNotEquals(TrailerColumns.NAME, value);
        return this;
    }

    public TrailerSelection nameLike(String... value) {
        addLike(TrailerColumns.NAME, value);
        return this;
    }

    public TrailerSelection nameContains(String... value) {
        addContains(TrailerColumns.NAME, value);
        return this;
    }

    public TrailerSelection nameStartsWith(String... value) {
        addStartsWith(TrailerColumns.NAME, value);
        return this;
    }

    public TrailerSelection nameEndsWith(String... value) {
        addEndsWith(TrailerColumns.NAME, value);
        return this;
    }

    public TrailerSelection orderByName(boolean desc) {
        orderBy(TrailerColumns.NAME, desc);
        return this;
    }

    public TrailerSelection orderByName() {
        orderBy(TrailerColumns.NAME, false);
        return this;
    }

    public TrailerSelection url(String... value) {
        addEquals(TrailerColumns.URL, value);
        return this;
    }

    public TrailerSelection urlNot(String... value) {
        addNotEquals(TrailerColumns.URL, value);
        return this;
    }

    public TrailerSelection urlLike(String... value) {
        addLike(TrailerColumns.URL, value);
        return this;
    }

    public TrailerSelection urlContains(String... value) {
        addContains(TrailerColumns.URL, value);
        return this;
    }

    public TrailerSelection urlStartsWith(String... value) {
        addStartsWith(TrailerColumns.URL, value);
        return this;
    }

    public TrailerSelection urlEndsWith(String... value) {
        addEndsWith(TrailerColumns.URL, value);
        return this;
    }

    public TrailerSelection orderByUrl(boolean desc) {
        orderBy(TrailerColumns.URL, desc);
        return this;
    }

    public TrailerSelection orderByUrl() {
        orderBy(TrailerColumns.URL, false);
        return this;
    }

    public TrailerSelection movieId(long... value) {
        addEquals(TrailerColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public TrailerSelection movieIdNot(long... value) {
        addNotEquals(TrailerColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public TrailerSelection movieIdGt(long value) {
        addGreaterThan(TrailerColumns.MOVIE_ID, value);
        return this;
    }

    public TrailerSelection movieIdGtEq(long value) {
        addGreaterThanOrEquals(TrailerColumns.MOVIE_ID, value);
        return this;
    }

    public TrailerSelection movieIdLt(long value) {
        addLessThan(TrailerColumns.MOVIE_ID, value);
        return this;
    }

    public TrailerSelection movieIdLtEq(long value) {
        addLessThanOrEquals(TrailerColumns.MOVIE_ID, value);
        return this;
    }

    public TrailerSelection orderByMovieId(boolean desc) {
        orderBy(TrailerColumns.MOVIE_ID, desc);
        return this;
    }

    public TrailerSelection orderByMovieId() {
        orderBy(TrailerColumns.MOVIE_ID, false);
        return this;
    }

    public TrailerSelection movieTitle(String... value) {
        addEquals(MovieColumns.TITLE, value);
        return this;
    }

    public TrailerSelection movieTitleNot(String... value) {
        addNotEquals(MovieColumns.TITLE, value);
        return this;
    }

    public TrailerSelection movieTitleLike(String... value) {
        addLike(MovieColumns.TITLE, value);
        return this;
    }

    public TrailerSelection movieTitleContains(String... value) {
        addContains(MovieColumns.TITLE, value);
        return this;
    }

    public TrailerSelection movieTitleStartsWith(String... value) {
        addStartsWith(MovieColumns.TITLE, value);
        return this;
    }

    public TrailerSelection movieTitleEndsWith(String... value) {
        addEndsWith(MovieColumns.TITLE, value);
        return this;
    }

    public TrailerSelection orderByMovieTitle(boolean desc) {
        orderBy(MovieColumns.TITLE, desc);
        return this;
    }

    public TrailerSelection orderByMovieTitle() {
        orderBy(MovieColumns.TITLE, false);
        return this;
    }

    public TrailerSelection movieDescription(String... value) {
        addEquals(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public TrailerSelection movieDescriptionNot(String... value) {
        addNotEquals(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public TrailerSelection movieDescriptionLike(String... value) {
        addLike(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public TrailerSelection movieDescriptionContains(String... value) {
        addContains(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public TrailerSelection movieDescriptionStartsWith(String... value) {
        addStartsWith(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public TrailerSelection movieDescriptionEndsWith(String... value) {
        addEndsWith(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public TrailerSelection orderByMovieDescription(boolean desc) {
        orderBy(MovieColumns.DESCRIPTION, desc);
        return this;
    }

    public TrailerSelection orderByMovieDescription() {
        orderBy(MovieColumns.DESCRIPTION, false);
        return this;
    }

    public TrailerSelection movieReleasedate(String... value) {
        addEquals(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public TrailerSelection movieReleasedateNot(String... value) {
        addNotEquals(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public TrailerSelection movieReleasedateLike(String... value) {
        addLike(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public TrailerSelection movieReleasedateContains(String... value) {
        addContains(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public TrailerSelection movieReleasedateStartsWith(String... value) {
        addStartsWith(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public TrailerSelection movieReleasedateEndsWith(String... value) {
        addEndsWith(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public TrailerSelection orderByMovieReleasedate(boolean desc) {
        orderBy(MovieColumns.RELEASEDATE, desc);
        return this;
    }

    public TrailerSelection orderByMovieReleasedate() {
        orderBy(MovieColumns.RELEASEDATE, false);
        return this;
    }

    public TrailerSelection movieRating(String... value) {
        addEquals(MovieColumns.RATING, value);
        return this;
    }

    public TrailerSelection movieRatingNot(String... value) {
        addNotEquals(MovieColumns.RATING, value);
        return this;
    }

    public TrailerSelection movieRatingLike(String... value) {
        addLike(MovieColumns.RATING, value);
        return this;
    }

    public TrailerSelection movieRatingContains(String... value) {
        addContains(MovieColumns.RATING, value);
        return this;
    }

    public TrailerSelection movieRatingStartsWith(String... value) {
        addStartsWith(MovieColumns.RATING, value);
        return this;
    }

    public TrailerSelection movieRatingEndsWith(String... value) {
        addEndsWith(MovieColumns.RATING, value);
        return this;
    }

    public TrailerSelection orderByMovieRating(boolean desc) {
        orderBy(MovieColumns.RATING, desc);
        return this;
    }

    public TrailerSelection orderByMovieRating() {
        orderBy(MovieColumns.RATING, false);
        return this;
    }

    public TrailerSelection movieImage(byte[]... value) {
        addEquals(MovieColumns.IMAGE, value);
        return this;
    }

    public TrailerSelection movieImageNot(byte[]... value) {
        addNotEquals(MovieColumns.IMAGE, value);
        return this;
    }


    public TrailerSelection orderByMovieImage(boolean desc) {
        orderBy(MovieColumns.IMAGE, desc);
        return this;
    }

    public TrailerSelection orderByMovieImage() {
        orderBy(MovieColumns.IMAGE, false);
        return this;
    }
}
