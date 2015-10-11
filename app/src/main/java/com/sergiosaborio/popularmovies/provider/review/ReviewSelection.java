package com.sergiosaborio.popularmovies.provider.review;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.sergiosaborio.popularmovies.provider.base.AbstractSelection;
import com.sergiosaborio.popularmovies.provider.movie.*;

/**
 * Selection for the {@code review} table.
 */
public class ReviewSelection extends AbstractSelection<ReviewSelection> {
    @Override
    protected Uri baseUri() {
        return ReviewColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ReviewCursor} object, which is positioned before the first entry, or null.
     */
    public ReviewCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ReviewCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public ReviewCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ReviewCursor} object, which is positioned before the first entry, or null.
     */
    public ReviewCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ReviewCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public ReviewCursor query(Context context) {
        return query(context, null);
    }


    public ReviewSelection id(long... value) {
        addEquals("review." + ReviewColumns._ID, toObjectArray(value));
        return this;
    }

    public ReviewSelection idNot(long... value) {
        addNotEquals("review." + ReviewColumns._ID, toObjectArray(value));
        return this;
    }

    public ReviewSelection orderById(boolean desc) {
        orderBy("review." + ReviewColumns._ID, desc);
        return this;
    }

    public ReviewSelection orderById() {
        return orderById(false);
    }

    public ReviewSelection name(String... value) {
        addEquals(ReviewColumns.NAME, value);
        return this;
    }

    public ReviewSelection nameNot(String... value) {
        addNotEquals(ReviewColumns.NAME, value);
        return this;
    }

    public ReviewSelection nameLike(String... value) {
        addLike(ReviewColumns.NAME, value);
        return this;
    }

    public ReviewSelection nameContains(String... value) {
        addContains(ReviewColumns.NAME, value);
        return this;
    }

    public ReviewSelection nameStartsWith(String... value) {
        addStartsWith(ReviewColumns.NAME, value);
        return this;
    }

    public ReviewSelection nameEndsWith(String... value) {
        addEndsWith(ReviewColumns.NAME, value);
        return this;
    }

    public ReviewSelection orderByName(boolean desc) {
        orderBy(ReviewColumns.NAME, desc);
        return this;
    }

    public ReviewSelection orderByName() {
        orderBy(ReviewColumns.NAME, false);
        return this;
    }

    public ReviewSelection review(String... value) {
        addEquals(ReviewColumns.REVIEW, value);
        return this;
    }

    public ReviewSelection reviewNot(String... value) {
        addNotEquals(ReviewColumns.REVIEW, value);
        return this;
    }

    public ReviewSelection reviewLike(String... value) {
        addLike(ReviewColumns.REVIEW, value);
        return this;
    }

    public ReviewSelection reviewContains(String... value) {
        addContains(ReviewColumns.REVIEW, value);
        return this;
    }

    public ReviewSelection reviewStartsWith(String... value) {
        addStartsWith(ReviewColumns.REVIEW, value);
        return this;
    }

    public ReviewSelection reviewEndsWith(String... value) {
        addEndsWith(ReviewColumns.REVIEW, value);
        return this;
    }

    public ReviewSelection orderByReview(boolean desc) {
        orderBy(ReviewColumns.REVIEW, desc);
        return this;
    }

    public ReviewSelection orderByReview() {
        orderBy(ReviewColumns.REVIEW, false);
        return this;
    }

    public ReviewSelection movieId(long... value) {
        addEquals(ReviewColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public ReviewSelection movieIdNot(long... value) {
        addNotEquals(ReviewColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public ReviewSelection movieIdGt(long value) {
        addGreaterThan(ReviewColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewSelection movieIdGtEq(long value) {
        addGreaterThanOrEquals(ReviewColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewSelection movieIdLt(long value) {
        addLessThan(ReviewColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewSelection movieIdLtEq(long value) {
        addLessThanOrEquals(ReviewColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewSelection orderByMovieId(boolean desc) {
        orderBy(ReviewColumns.MOVIE_ID, desc);
        return this;
    }

    public ReviewSelection orderByMovieId() {
        orderBy(ReviewColumns.MOVIE_ID, false);
        return this;
    }

    public ReviewSelection movieTitle(String... value) {
        addEquals(MovieColumns.TITLE, value);
        return this;
    }

    public ReviewSelection movieTitleNot(String... value) {
        addNotEquals(MovieColumns.TITLE, value);
        return this;
    }

    public ReviewSelection movieTitleLike(String... value) {
        addLike(MovieColumns.TITLE, value);
        return this;
    }

    public ReviewSelection movieTitleContains(String... value) {
        addContains(MovieColumns.TITLE, value);
        return this;
    }

    public ReviewSelection movieTitleStartsWith(String... value) {
        addStartsWith(MovieColumns.TITLE, value);
        return this;
    }

    public ReviewSelection movieTitleEndsWith(String... value) {
        addEndsWith(MovieColumns.TITLE, value);
        return this;
    }

    public ReviewSelection orderByMovieTitle(boolean desc) {
        orderBy(MovieColumns.TITLE, desc);
        return this;
    }

    public ReviewSelection orderByMovieTitle() {
        orderBy(MovieColumns.TITLE, false);
        return this;
    }

    public ReviewSelection movieDescription(String... value) {
        addEquals(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public ReviewSelection movieDescriptionNot(String... value) {
        addNotEquals(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public ReviewSelection movieDescriptionLike(String... value) {
        addLike(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public ReviewSelection movieDescriptionContains(String... value) {
        addContains(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public ReviewSelection movieDescriptionStartsWith(String... value) {
        addStartsWith(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public ReviewSelection movieDescriptionEndsWith(String... value) {
        addEndsWith(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public ReviewSelection orderByMovieDescription(boolean desc) {
        orderBy(MovieColumns.DESCRIPTION, desc);
        return this;
    }

    public ReviewSelection orderByMovieDescription() {
        orderBy(MovieColumns.DESCRIPTION, false);
        return this;
    }

    public ReviewSelection movieReleasedate(String... value) {
        addEquals(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public ReviewSelection movieReleasedateNot(String... value) {
        addNotEquals(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public ReviewSelection movieReleasedateLike(String... value) {
        addLike(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public ReviewSelection movieReleasedateContains(String... value) {
        addContains(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public ReviewSelection movieReleasedateStartsWith(String... value) {
        addStartsWith(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public ReviewSelection movieReleasedateEndsWith(String... value) {
        addEndsWith(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public ReviewSelection orderByMovieReleasedate(boolean desc) {
        orderBy(MovieColumns.RELEASEDATE, desc);
        return this;
    }

    public ReviewSelection orderByMovieReleasedate() {
        orderBy(MovieColumns.RELEASEDATE, false);
        return this;
    }

    public ReviewSelection movieRating(String... value) {
        addEquals(MovieColumns.RATING, value);
        return this;
    }

    public ReviewSelection movieRatingNot(String... value) {
        addNotEquals(MovieColumns.RATING, value);
        return this;
    }

    public ReviewSelection movieRatingLike(String... value) {
        addLike(MovieColumns.RATING, value);
        return this;
    }

    public ReviewSelection movieRatingContains(String... value) {
        addContains(MovieColumns.RATING, value);
        return this;
    }

    public ReviewSelection movieRatingStartsWith(String... value) {
        addStartsWith(MovieColumns.RATING, value);
        return this;
    }

    public ReviewSelection movieRatingEndsWith(String... value) {
        addEndsWith(MovieColumns.RATING, value);
        return this;
    }

    public ReviewSelection orderByMovieRating(boolean desc) {
        orderBy(MovieColumns.RATING, desc);
        return this;
    }

    public ReviewSelection orderByMovieRating() {
        orderBy(MovieColumns.RATING, false);
        return this;
    }

    public ReviewSelection movieImage(byte[]... value) {
        addEquals(MovieColumns.IMAGE, value);
        return this;
    }

    public ReviewSelection movieImageNot(byte[]... value) {
        addNotEquals(MovieColumns.IMAGE, value);
        return this;
    }


    public ReviewSelection orderByMovieImage(boolean desc) {
        orderBy(MovieColumns.IMAGE, desc);
        return this;
    }

    public ReviewSelection orderByMovieImage() {
        orderBy(MovieColumns.IMAGE, false);
        return this;
    }
}
