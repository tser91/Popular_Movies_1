package com.sergiosaborio.popularmovies;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SergioSaborio on 8/24/15.
 */
public class MovieCollection {

    public static List<Movie> movieCollection;

    public MovieCollection() {
        movieCollection = new ArrayList<>();
    }

    public MovieCollection(List<Movie> movieCollection) {
        MovieCollection.movieCollection = movieCollection;
    }

    public List<Movie> getMovieCollection() {
        return movieCollection;
    }

    public void setMovieCollection(List<Movie> movieCollection) {
        MovieCollection.movieCollection = movieCollection;
    }

    public Movie getMovie(String title) {
        for (int i =0; i < movieCollection.size(); i++){
            if (movieCollection.get(i).getTitle().equals(title)){
                return movieCollection.get(i);
            }
        }
        return null;
    }

    public Movie getMovie(int position){
        if (position >= movieCollection.size()){
            return null;
        }
        return movieCollection.get(position);
    }

    public void addMovie(Movie movie){
        movieCollection.add(movie);
    }

    public void removeMovie(Movie movie){
        for (int i =0; i < movieCollection.size(); i++){
            if (movieCollection.get(i).equals(movie)){
                movieCollection.remove(i);
            }
        }
    }

    public void removeMovie(String title){
        for (int i =0; i < movieCollection.size(); i++){
            if (movieCollection.get(i).getTitle().equals(title)){
                movieCollection.remove(i);
            }
        }
    }

    public void clearMovieCollection(){
        movieCollection.clear();
    }

}
