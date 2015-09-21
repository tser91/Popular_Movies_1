package com.sergiosaborio.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import hugo.weaving.DebugLog;

/**
 * Created by SergioSaborio on 8/24/15.
 */
public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    /* Class attributes */
    private String title;
    private String release_date;
    private String movie_poster_url;
    private double vote_average;
    private String plot_synopsis;
    private int id;

    /* Object Constructors */
    public Movie(String title, String release_date, String movie_poster_url,
                 int vote_average, String plot_synopsis, int id) {
        this.title = title;
        this.release_date = release_date;
        this.movie_poster_url = movie_poster_url;
        this.vote_average = vote_average;
        this.plot_synopsis = plot_synopsis;
        this.id = id;
    }


    public Movie() {
        this.title = "";
        this.release_date = "";
        this.movie_poster_url = "";
        this.vote_average = 0;
        this.plot_synopsis = "";
        this.id = 0;
    }

    protected Movie(Parcel in) {
        title = in.readString();
        release_date = in.readString();
        movie_poster_url = in.readString();
        vote_average = in.readDouble();
        plot_synopsis = in.readString();
        id = in.readInt();
    }

    /* Getters and Setters */
    @DebugLog
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @DebugLog
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @DebugLog
    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @DebugLog
    public String getMovie_poster_url() {
        return movie_poster_url;
    }

    public void setMovie_poster_url(String movie_poster_url) {
        this.movie_poster_url = movie_poster_url;
    }

    @DebugLog
    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    @DebugLog
    public String getPlot_synopsis() {
        return plot_synopsis;
    }

    public void setPlot_synopsis(String plot_synopsis) {
        this.plot_synopsis = plot_synopsis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(release_date);
        dest.writeString(movie_poster_url);
        dest.writeDouble(vote_average);
        dest.writeString(plot_synopsis);
        dest.writeInt(id);
    }
}
