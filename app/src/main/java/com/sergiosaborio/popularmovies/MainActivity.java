package com.sergiosaborio.popularmovies;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Reference:
 * https://github.com/dagingaa/android-tmdb-example/blob/master/src/com/daginge/tmdbsearch/
 * TMDBSearchResultActivity.java
 */

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        constants {

    // UI Elements
    GridView gridView;
    Spinner spinner;

    // Logic variables
    MovieCollection movieCollection;
    String sortingCriteria;
    ArrayAdapter<CharSequence> sort_criteria_spinner_adapter;

    boolean changed_orientation= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializations
        gridView = (GridView) findViewById(R.id.grid_posters);
        spinner = (Spinner) findViewById(R.id.spinner);
        movieCollection = new MovieCollection();
        sortingCriteria = "";

        // Create an ArrayAdapter using the string array and a default spinner layout
        sort_criteria_spinner_adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_criteria_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        sort_criteria_spinner_adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(sort_criteria_spinner_adapter);

        spinner.setOnItemSelectedListener(this);

        // Associates the grid adapter with the movie collection ( a Movie object list)
        gridView.setAdapter(new MovieThumbAdapter(getApplicationContext(), R.layout.movie_cell,
                movieCollection.getMovieCollection()));
        // If the user gets to the end of the UI, then more movies will be fetched
        gridView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                /*if (changed_orientation){
                    changed_orientation = false;
                }
                else {*/
                    loadDataFromApi(page);
                //}
            }
        });

        if (savedInstanceState != null){
            changed_orientation = true;
            if (savedInstanceState.get(MOVIE_LIST_KEY) != null){
                List<Movie> savedMovieList = (List<Movie>) savedInstanceState.get(MOVIE_LIST_KEY);
                sortingCriteria = savedInstanceState.getString(SORTING_CRITERIA_KEY);
                for (int i = 0; i < savedMovieList.size(); i++){
                    movieCollection.addMovie(savedMovieList.get(i));
                }
                updateUI();
            }
        }
        else {
            // Perform the first movie search to display initial options to the user
            loadDataFromApi(FIRST_PAGE);
        }
    }

    private void loadDataFromApi(int page) {
        String query = BASE_MOVIEDB_QUERY_URL;
        query += DISCOVER_MOVIE + KEY_PARAMETER + MOVIEDB_KEY + PAGE_PARAMETER + page +
                SORT_PARAMETER + sortingCriteria;
        Log.d("URL query", query);
        new TMDBConnection().execute(query);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIE_LIST_KEY,
                (ArrayList<? extends Parcelable>) movieCollection.getMovieCollection());
        outState.putString(SORTING_CRITERIA_KEY, sortingCriteria);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private void resetGridAdapter() {
        movieCollection.clearMovieCollection();
        loadDataFromApi(FIRST_PAGE);
    }

    private void updateUI() {
        ((BaseAdapter) gridView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String previousSortingCriteria = sortingCriteria;
        switch (position) {
            case 1:
                sortingCriteria = MENU_RELEASE_DATE;
                break;
            case 2:
                sortingCriteria = MENU_REVENUE;
                break;
            case 3:
                sortingCriteria = MENU_HIGHEST_RATED;
                break;
            case 4:
                sortingCriteria = MENU_MOST_POPULAR;
                break;
            case 5:
                sortingCriteria = MENU_NAME;
                break;
            default:
                sortingCriteria = "";
                break;
        }
        // If the sorting criteria didn't change, there's nothing to be made
        if (!previousSortingCriteria.equals(sortingCriteria)){
            resetGridAdapter();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class TMDBConnection extends AsyncTask {

        ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(LOADING_MOVIES);
            this.dialog.show();
        }

        @Override
        protected Object doInBackground(Object... params) {
            try {
                getMoviesInfo((String) params[0]);
            } catch (IOException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            updateUI();
        }

        public void getMoviesInfo(String query) throws IOException {
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
                parseResult(stringify(stream));
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
        }

        private void parseResult(String result) {

            ArrayList<String> results = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray array = (JSONArray) jsonObject.get("results");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonMovieObject = array.getJSONObject(i);
                    Movie movie = new Movie();
                    movie.setTitle(jsonMovieObject.getString("title"));
                    movie.setMovie_poster_url(jsonMovieObject.getString("poster_path"));
                    movie.setPlot_synopsis(jsonMovieObject.getString("overview"));
                    movie.setRelease_date(jsonMovieObject.getString("release_date"));
                    movie.setVote_average(Double.parseDouble(
                            jsonMovieObject.getString("vote_average")));
                    movieCollection.addMovie(movie);
                }
            } catch (JSONException e) {
                System.err.println(e);
            }
        }

        public String stringify(InputStream stream) throws IOException {
            Reader reader;
            reader = new InputStreamReader(stream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            return bufferedReader.readLine();
        }
    }
}
