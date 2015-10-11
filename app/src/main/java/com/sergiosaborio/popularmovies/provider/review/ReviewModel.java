package com.sergiosaborio.popularmovies.provider.review;

import com.sergiosaborio.popularmovies.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code review} table.
 */
public interface ReviewModel extends BaseModel {

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    String getName();

    /**
     * Get the {@code review} value.
     * Can be {@code null}.
     */
    @Nullable
    String getReview();

    /**
     * Get the {@code movie_id} value.
     */
    long getMovieId();
}
