package com.sergiosaborio.popularmovies.provider.movie;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.sergiosaborio.popularmovies.provider.base.AbstractSelection;

/**
 * Selection for the {@code movie} table.
 */
public class MovieSelection extends AbstractSelection<MovieSelection> {
    @Override
    protected Uri baseUri() {
        return MovieColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code MovieCursor} object, which is positioned before the first entry, or null.
     */
    public MovieCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new MovieCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public MovieCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code MovieCursor} object, which is positioned before the first entry, or null.
     */
    public MovieCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new MovieCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public MovieCursor query(Context context) {
        return query(context, null);
    }


    public MovieSelection id(long... value) {
        addEquals("movie." + MovieColumns._ID, toObjectArray(value));
        return this;
    }

    public MovieSelection idNot(long... value) {
        addNotEquals("movie." + MovieColumns._ID, toObjectArray(value));
        return this;
    }

    public MovieSelection orderById(boolean desc) {
        orderBy("movie." + MovieColumns._ID, desc);
        return this;
    }

    public MovieSelection orderById() {
        return orderById(false);
    }

    public MovieSelection title(String... value) {
        addEquals(MovieColumns.TITLE, value);
        return this;
    }

    public MovieSelection titleNot(String... value) {
        addNotEquals(MovieColumns.TITLE, value);
        return this;
    }

    public MovieSelection titleLike(String... value) {
        addLike(MovieColumns.TITLE, value);
        return this;
    }

    public MovieSelection titleContains(String... value) {
        addContains(MovieColumns.TITLE, value);
        return this;
    }

    public MovieSelection titleStartsWith(String... value) {
        addStartsWith(MovieColumns.TITLE, value);
        return this;
    }

    public MovieSelection titleEndsWith(String... value) {
        addEndsWith(MovieColumns.TITLE, value);
        return this;
    }

    public MovieSelection orderByTitle(boolean desc) {
        orderBy(MovieColumns.TITLE, desc);
        return this;
    }

    public MovieSelection orderByTitle() {
        orderBy(MovieColumns.TITLE, false);
        return this;
    }

    public MovieSelection description(String... value) {
        addEquals(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public MovieSelection descriptionNot(String... value) {
        addNotEquals(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public MovieSelection descriptionLike(String... value) {
        addLike(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public MovieSelection descriptionContains(String... value) {
        addContains(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public MovieSelection descriptionStartsWith(String... value) {
        addStartsWith(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public MovieSelection descriptionEndsWith(String... value) {
        addEndsWith(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public MovieSelection orderByDescription(boolean desc) {
        orderBy(MovieColumns.DESCRIPTION, desc);
        return this;
    }

    public MovieSelection orderByDescription() {
        orderBy(MovieColumns.DESCRIPTION, false);
        return this;
    }

    public MovieSelection releasedate(String... value) {
        addEquals(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public MovieSelection releasedateNot(String... value) {
        addNotEquals(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public MovieSelection releasedateLike(String... value) {
        addLike(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public MovieSelection releasedateContains(String... value) {
        addContains(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public MovieSelection releasedateStartsWith(String... value) {
        addStartsWith(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public MovieSelection releasedateEndsWith(String... value) {
        addEndsWith(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public MovieSelection orderByReleasedate(boolean desc) {
        orderBy(MovieColumns.RELEASEDATE, desc);
        return this;
    }

    public MovieSelection orderByReleasedate() {
        orderBy(MovieColumns.RELEASEDATE, false);
        return this;
    }

    public MovieSelection rating(String... value) {
        addEquals(MovieColumns.RATING, value);
        return this;
    }

    public MovieSelection ratingNot(String... value) {
        addNotEquals(MovieColumns.RATING, value);
        return this;
    }

    public MovieSelection ratingLike(String... value) {
        addLike(MovieColumns.RATING, value);
        return this;
    }

    public MovieSelection ratingContains(String... value) {
        addContains(MovieColumns.RATING, value);
        return this;
    }

    public MovieSelection ratingStartsWith(String... value) {
        addStartsWith(MovieColumns.RATING, value);
        return this;
    }

    public MovieSelection ratingEndsWith(String... value) {
        addEndsWith(MovieColumns.RATING, value);
        return this;
    }

    public MovieSelection orderByRating(boolean desc) {
        orderBy(MovieColumns.RATING, desc);
        return this;
    }

    public MovieSelection orderByRating() {
        orderBy(MovieColumns.RATING, false);
        return this;
    }

    public MovieSelection image(byte[]... value) {
        addEquals(MovieColumns.IMAGE, value);
        return this;
    }

    public MovieSelection imageNot(byte[]... value) {
        addNotEquals(MovieColumns.IMAGE, value);
        return this;
    }


    public MovieSelection orderByImage(boolean desc) {
        orderBy(MovieColumns.IMAGE, desc);
        return this;
    }

    public MovieSelection orderByImage() {
        orderBy(MovieColumns.IMAGE, false);
        return this;
    }
}
