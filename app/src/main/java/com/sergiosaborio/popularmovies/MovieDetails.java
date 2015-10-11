package com.sergiosaborio.popularmovies;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sergiosaborio.popularmovies.provider.movie.MovieColumns;
import com.sergiosaborio.popularmovies.provider.movie.MovieContentValues;
import com.sergiosaborio.popularmovies.provider.movie.MovieCursor;
import com.sergiosaborio.popularmovies.provider.movie.MovieSelection;
import com.sergiosaborio.popularmovies.provider.trailer.TrailerColumns;
import com.sergiosaborio.popularmovies.provider.trailer.TrailerContentValues;
import com.sergiosaborio.popularmovies.provider.trailer.TrailerCursor;
import com.sergiosaborio.popularmovies.provider.trailer.TrailerSelection;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import hugo.weaving.DebugLog;

public class MovieDetails extends AppCompatActivity implements constants {

    private Movie movie;
    private ListView listViewTrailers;
    private ListView listViewReviews;
    private ArrayList<Trailer> trailersArray;
    private boolean isMovieFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        trailersArray = new ArrayList<>();

        isMovieFavorite = false;

        // Create the adapter to convert the array to views
        // Attach the adapter to a ListView
        listViewTrailers = (ListView) findViewById(R.id.listView_movie_trailers);
        listViewTrailers.setAdapter(new TrailerAdapter(getApplicationContext(),
                R.layout.trailer_cell, trailersArray));

        listViewTrailers.setClickable(true);
        listViewTrailers.setItemsCanFocus(false);

        final ImageButton button = (ImageButton) findViewById(R.id.button_favorite);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if (button.isSelected()) {
                    button.setImageResource(android.R.drawable.btn_star_big_off);
                    removeTrailers();
                    removeReviews();
                    removeMovie();
                } else {
                    button.setImageResource(android.R.drawable.btn_star_big_on);
                    long movieID = insertMovie();
                    insertTrailers(movieID);
                    insertReviews(movieID);
                }
                button.setSelected(!button.isSelected());
            }
        });

        // Get the movie information from main activity
        movie = getIntent().getParcelableExtra("movie");
        isMovieFavorite = isMovieFavorite();

        updateUI();

        if (savedInstanceState != null){
            updateAdapters();
        }
        else {
            // Check if movie is favorite, then load adapters from DB
            if (isMovieFavorite) {
                new TrailersCharge().execute();
                button.setImageResource(android.R.drawable.btn_star_big_on);
                button.setSelected(true);
            }
            else {
                loadDataFromApi(FIRST_PAGE);
            }
        }
    }

    private void removeReviews() {
    }

    private void removeTrailers() {
        MovieSelection movieSelection = new MovieSelection();
        movieSelection.title(movie.getTitle());
        String[] projection = { MovieColumns._ID};
        MovieCursor movieCursor = movieSelection.query(getContentResolver(), projection);
        movieCursor.moveToNext();

        TrailerSelection trailerSelection = new TrailerSelection();
        System.out.println("ID DE LA Q SE VA A BORRAR ES " + movieCursor.getId());
        trailerSelection.movieId(movieCursor.getId());
        trailerSelection.delete(getContentResolver());
    }

    private void removeMovie() {
        MovieSelection movieSelection = new MovieSelection();
        movieSelection.title(movie.getTitle());
        movieSelection.delete(getContentResolver());
    }

    @DebugLog
    private boolean isMovieFavorite() {
        MovieSelection movieSelection = new MovieSelection();
        movieSelection.title(movie.getTitle());
        String[] projection = { MovieColumns._ID};
        MovieCursor movieCursor = movieSelection.query(getContentResolver(), projection);
        movieCursor.moveToNext();
        return movieCursor.getCount() > 0;
    }

    /**
     * Insert the trailers of the movie.
     *
     */
    @DebugLog
    private void insertTrailers(long movieID) {
        for (int index = 0; index < trailersArray.size(); index++)
        {
            TrailerContentValues trailerValues = new TrailerContentValues();
            trailerValues.putName(trailersArray.get(index).getName());
            trailerValues.putUrl(trailersArray.get(index).getKey());
            trailerValues.putMovieId(movieID);
            Uri uri = trailerValues.insert(getContentResolver());
            ContentUris.parseId(uri);
        }
    }

    /**
     * Insert a reviews of the movie.
     *
     */
    @DebugLog
    private void insertReviews(long movieID) {
    }

    /**
     * Insert a movie.
     *
     * @return the id of the movie product.
     */
    @DebugLog
    private long insertMovie() {
        MovieContentValues movieValues = new MovieContentValues();
        movieValues.putDescription(movie.getPlot_synopsis());
        movieValues.putRating(String.valueOf(movie.getVote_average()));
        movieValues.putReleasedate(movie.getRelease_date());
        movieValues.putTitle(movie.getTitle());
        movieValues.putImage(getMovieImage());
        Uri uri = movieValues.insert(getContentResolver());
        return ContentUris.parseId(uri);
    }

    private byte[] getMovieImage() {
        // convert View into Bitmap
        ImageView view = (ImageView)findViewById(R.id.imageView_movied);
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bm = view.getDrawingCache();

        // convert Bitmap* into ByteArray
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void updateUI() {
        TextView textview;

        // Update movie poster
        Context context = this.getApplicationContext();
        ImageView image = (ImageView) findViewById(R.id.imageView_movied);
        image.setMaxHeight((int) (image.getWidth() / HEIGHT_PROPORTION));
        // Check if there is no poster available for the image
        if (movie.getMovie_poster_url().equals("null")) {
            Picasso.with(context).load(IMAGE_NOT_AVAILABLE_URL).fit().into(
                    (ImageView) findViewById(R.id.imageView_movied));
        }
        else if (isMovieFavorite)
        {
            MovieSelection movieSelection = new MovieSelection();
            movieSelection.title(movie.getTitle());
            String[] projection = { MovieColumns.IMAGE};
            MovieCursor movieCursor = movieSelection.query(getContentResolver(), projection);
            movieCursor.moveToNext();

            byte[] bytes = movieCursor.getImage();
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ((ImageView) findViewById(R.id.imageView_movied)).setImageBitmap(bmp);
            movieCursor.close();
        }
        else {
            Picasso.with(context).load(BASE_IMAGE_URL +
                    movie.getMovie_poster_url()).fit().into(
                    (ImageView) findViewById(R.id.imageView_movied));
        }

        //Update movie title
        textview = (TextView) findViewById(R.id.textView_movied_title);
        if (movie.getTitle().equals("null")) {
            textview.setText(TITLE_NOT_AVAILABLE);
        } else {
            textview.setText(movie.getTitle());
        }

        //Update movie release date
        textview = (TextView) findViewById(R.id.textView_movied_releaseDate);
        if (movie.getRelease_date().equals("null")) {
            textview.setText(RELEASE_DATE_NOT_AVAILABLE);
        } else {
            textview.setText(movie.getRelease_date());
        }

        //Update movie plot
        textview = (TextView) findViewById(R.id.textView_movied_plot);
        if (movie.getPlot_synopsis().equals("null")) {
            textview.setText(DESCRIPTION_NOT_AVAILABLE);
        } else {
            textview.setText(movie.getPlot_synopsis());
        }

        //Update movie rating
        textview = (TextView) findViewById(R.id.textView_moviedb_rating);
        if ((movie.getVote_average() + "").equals("null")) {
            textview.setText(RATING_NOT_AVAILABLE);
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

    private void loadDataFromApi(int page) {
        new TMDBConnection().execute(new QueryBuilder().getMovieReviews(page, movie.getId()),
                REVIEWS_QUERY);
        new TMDBConnection().execute(new QueryBuilder().getMovieTrailers(page, movie.getId()),
                VIDEOS_QUERY);
    }

    private void getMovieTrailers(String jsonInfo){
        try {
            JSONObject jsonObject = new JSONObject(jsonInfo);
            JSONArray array = (JSONArray) jsonObject.get("results");
            ArrayList<Trailer> trailers = Trailer.fromJson(array);
            for (int i = 0; i < trailers.size(); i++)
            {
                trailersArray.add(trailers.get(i));
            }

        } catch (JSONException e) {
            Log.e(APP_TAG, "STACKTRACE");
            Log.e(APP_TAG, Log.getStackTraceString(e));
        }
    }

    private void getMovieReviews(String jsonInfo){
        ArrayList<Review> reviewsArray;
        try {
            JSONObject jsonObject = new JSONObject(jsonInfo);
            JSONArray array = (JSONArray) jsonObject.get("results");
            reviewsArray = Review.fromJson(array);

            // Create the adapter to convert the array to views
            ReviewAdapter reviewAdapter= new ReviewAdapter(this, reviewsArray);

            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.listView_movie_reviews);
            //listView.setAdapter(reviewAdapter);

        } catch (JSONException e) {
            Log.e(APP_TAG, "STACKTRACE");
            Log.e(APP_TAG, Log.getStackTraceString(e));
        }
    }

    private void updateAdapters() {
        ((BaseAdapter) listViewTrailers.getAdapter()).notifyDataSetChanged();
        //((BaseAdapter) listViewReviews.getAdapter()).notifyDataSetChanged();
    }

    private class TMDBConnection extends AsyncTask {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Object doInBackground(Object... params) {
            try {
                String result = getMovieInfo((String) params[0]);
                if (params[1].equals(REVIEWS_QUERY)){
                    getMovieReviews(result);
                }
                else if (params[1].equals(VIDEOS_QUERY)) {
                    getMovieTrailers(result);
                }
            } catch (IOException e) {
                Log.e(APP_TAG, "STACKTRACE");
                Log.e(APP_TAG, Log.getStackTraceString(e));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            updateAdapters();
        }

        public String getMovieInfo(String query) throws IOException {
            URL url = new URL(query);

            InputStream stream = null;
            try {
                // Establish a connection
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.addRequestProperty("Accept", "application/json"); // Required to get TMDB to play nicely.
                conn.setDoInput(true);
                conn.connect();

                stream = conn.getInputStream();
                return stringify(stream);
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
        }

        public String stringify(InputStream stream) throws IOException {
            Reader reader;
            reader = new InputStreamReader(stream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            return bufferedReader.readLine();
        }
    }

    private class TrailersCharge extends AsyncTask {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Object doInBackground(Object[] params) {
            trailersArray.clear();

            // Get movie ID from DB
            MovieSelection movieSelection = new MovieSelection();
            movieSelection.title(movie.getTitle());
            String[] movieProjection = { MovieColumns._ID};
            MovieCursor movieCursor = movieSelection.query(getContentResolver(), movieProjection);
            movieCursor.moveToNext();

            // Get the trailers of the movie
            TrailerSelection trailerSelection = new TrailerSelection();
            trailerSelection.movieId(movieCursor.getId());

            String[] trailerProjection = {TrailerColumns._ID, TrailerColumns.NAME,
                    TrailerColumns.URL, TrailerColumns.MOVIE_ID};
            TrailerCursor trailerCursor = trailerSelection.query(getContentResolver(), trailerProjection);
            trailerCursor.moveToNext();

            for (int index = 0; index < trailerCursor.getCount(); index++)
            {
                Trailer trailer = new Trailer(trailerCursor.getUrl(), trailerCursor.getName(),
                        String.valueOf(trailerCursor.getMovieId()));
                trailersArray.add(trailer);
                trailerCursor.moveToNext();
            }
            trailerCursor.close();
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            updateAdapters();
        }
    }
}
