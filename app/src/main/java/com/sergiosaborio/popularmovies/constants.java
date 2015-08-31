package com.sergiosaborio.popularmovies;

/**
 * Created by SergioSaborio on 8/30/15.
 * Reference: http://www.mobiletoones.com/downloads/wallpapers/funny_wallpapers/preview/23/33618-sorry-this-wallpaer-not-available.jpg
 */

/*
  GETTING A MOVIEDB KEY:

 To fetch popular movies, you will use the API from themoviedb.org.
 If you don’t already have an account, you will need to create one in order to request an API Key.
 In your request for a key, state that your usage will be for educational/non-commercial use.
 You will also need to provide some personal information to complete the request.
 Once you submit your request, you should receive your key via email shortly after.
 */
public interface constants {

    String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    String MOVIEDB_KEY = ""; // <----------------------------------- PUT HERE YOUR MOVIEDB_KEY
    String MENU_MOST_POPULAR = "popularity.desc";
    String MENU_HIGHEST_RATED = "vote_average.desc";
    String MENU_NAME = "original_title.desc";
    String MENU_RELEASE_DATE = "primary_release_date.desc";
    String MENU_REVENUE = "revenue.desc";
    String TOTAL_RATING = "/10";
    String BASE_MOVIEDB_QUERY_URL = "http://api.themoviedb.org/3/";
    String DISCOVER_MOVIE = "discover/movie";
    String KEY_PARAMETER = "?api_key=";
    String PAGE_PARAMETER = "&page=";
    String SORT_PARAMETER = "&sort_by=";
    String IMAGE_NOT_AVAILABLE_URL = "http://www.mobiletoones.com/downloads/wallpapers/" +
            "funny_wallpapers/preview/23/33618-sorry-this-wallpaer-not-available.jpg";
    String TITLE_NOT_AVAILIBLE = "Movie's title not available";
    String DESCRIPTION_NOT_AVAILIBLE = "Sorry, this movie's description is'nt available";
    String RELEASE_DATE_NOT_AVAILIBLE = "Release date not available";
    String RATING_NOT_AVAILIBLE = "Rating not available";
    int FIRST_PAGE = 1;
    double HEIGHT_PROPORTION = 0.675;
}
