package com.sergiosaborio.popularmovies;

import java.io.Serializable;

/**
 * Created by SergioSaborio on 8/24/15.
 */
public class Movie implements Serializable {

    /* Class attributes */
    private String title;
    private String release_date;
    private String movie_poster_url;
    private double vote_average;
    private String plot_synopsis;

    /* Object Constructors */
    public Movie(String title, String release_date, String movie_poster_url,
                 int vote_average, String plot_synopsis) {
        this.title = title;
        this.release_date = release_date;
        this.movie_poster_url = movie_poster_url;
        this.vote_average = vote_average;
        this.plot_synopsis = plot_synopsis;
    }

    public Movie() {
        this.title = "";
        this.release_date = "";
        this.movie_poster_url = "";
        this.vote_average = 0;
        this.plot_synopsis = "";
    }


    /* Getters and Setters */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getMovie_poster_url() {
        return movie_poster_url;
    }

    public void setMovie_poster_url(String movie_poster_url) {
        this.movie_poster_url = movie_poster_url;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getPlot_synopsis() {
        return plot_synopsis;
    }

    public void setPlot_synopsis(String plot_synopsis) {
        this.plot_synopsis = plot_synopsis;
    }
}
