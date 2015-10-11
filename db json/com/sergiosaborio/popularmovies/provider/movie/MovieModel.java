package com.sergiosaborio.popularmovies.provider.movie;

import com.sergiosaborio.popularmovies.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code movie} table.
 */
public interface MovieModel extends BaseModel {

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    String getTitle();

    /**
     * Get the {@code description} value.
     * Can be {@code null}.
     */
    @Nullable
    String getDescription();

    /**
     * Get the {@code releasedate} value.
     * Can be {@code null}.
     */
    @Nullable
    String getReleasedate();

    /**
     * Get the {@code rating} value.
     * Can be {@code null}.
     */
    @Nullable
    String getRating();

    /**
     * Get the {@code image} value.
     * Can be {@code null}.
     */
    @Nullable
    byte[] getImage();
}
