package com.sergiosaborio.popularmovies.provider.trailer;

import com.sergiosaborio.popularmovies.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code trailer} table.
 */
public interface TrailerModel extends BaseModel {

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    String getName();

    /**
     * Get the {@code url} value.
     * Can be {@code null}.
     */
    @Nullable
    String getUrl();

    /**
     * Get the {@code movie_id} value.
     */
    long getMovieId();
}
