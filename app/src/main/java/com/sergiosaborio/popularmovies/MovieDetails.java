package com.sergiosaborio.popularmovies;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity implements constants {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        
        // Get the movie information from main activity
        Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        Log.d("Movie title", movie.getTitle());
        updateUI(movie);
    }

    private void updateUI(Movie movie) {
        TextView textview;

        // Update movie poster
        Context context = this.getApplicationContext();
        ImageView image = (ImageView) findViewById(R.id.imageView_movied);
        image.setMaxHeight((int) (image.getWidth() / HEIGHT_PROPORTION));
        // Check if there is no poster available for the image
        if (movie.getMovie_poster_url().equals("null")) {
            Picasso.with(context).load(IMAGE_NOT_AVAILABLE_URL).fit().into(
                    (ImageView) findViewById(R.id.imageView_movied));
        } else {
            Picasso.with(context).load(BASE_IMAGE_URL +
                    movie.getMovie_poster_url()).fit().into(
                    (ImageView) findViewById(R.id.imageView_movied));
        }

        //Update movie title
        textview = (TextView) findViewById(R.id.textView_movied_title);
        if (movie.getTitle().equals("null")) {
            textview.setText(TITLE_NOT_AVAILIBLE);
        } else {
            textview.setText(movie.getTitle());
        }

        //Update movie release date
        textview = (TextView) findViewById(R.id.textView_movied_releaseDate);
        if (movie.getRelease_date().equals("null")) {
            textview.setText(RELEASE_DATE_NOT_AVAILIBLE);
        } else {
            textview.setText(movie.getRelease_date());
        }

        //Update movie plot
        textview = (TextView) findViewById(R.id.textView_movied_plot);
        if (movie.getPlot_synopsis().equals("null")) {
            textview.setText(DESCRIPTION_NOT_AVAILIBLE);
        } else {
            textview.setText(movie.getPlot_synopsis());
        }

        //Update movie rating
        textview = (TextView) findViewById(R.id.textView_moviedb_rating);
        if ((movie.getVote_average() + "").equals("null")) {
            textview.setText(RATING_NOT_AVAILIBLE);
        } else {
            textview.setText(movie.getVote_average() + TOTAL_RATING);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
