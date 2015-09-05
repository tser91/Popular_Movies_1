package com.sergiosaborio.popularmovies;

import android.util.Log;

/**
 * Created by SergioSaborio on 9/5/15.
 */
public class QueryBuilder implements constants {

    public QueryBuilder() {
    }

    public String getDiscoverQuery(int page, String sortingCriteria){
        String query = BASE_MOVIEDB_QUERY_URL + DISCOVER_MOVIE + KEY_PARAMETER + MOVIEDB_KEY +
                PAGE_PARAMETER + page +
                SORT_PARAMETER + sortingCriteria;
        Log.d("URL query", query);
        return query;
    }

    public String getMovieTrailers(int page, int movieId){
        String query = BASE_MOVIEDB_QUERY_URL + MOVIE_QUERY + movieId + VIDEOS_QUERY +
                KEY_PARAMETER + MOVIEDB_KEY +
                PAGE_PARAMETER + page;
        Log.d("URL query", query);
        return query;
    }

    public String getMovieReviews(int page, int movieId){
        String query = BASE_MOVIEDB_QUERY_URL + MOVIE_QUERY + movieId + REVIEWS_QUERY +
                KEY_PARAMETER + MOVIEDB_KEY +
                PAGE_PARAMETER + page;
        Log.d("URL query", query);
        return query;
    }

}
