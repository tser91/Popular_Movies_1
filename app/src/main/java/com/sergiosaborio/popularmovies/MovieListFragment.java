package com.sergiosaborio.popularmovies;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import com.sergiosaborio.popularmovies.provider.movie.MovieColumns;
import com.sergiosaborio.popularmovies.provider.movie.MovieCursor;
import com.sergiosaborio.popularmovies.provider.movie.MovieSelection;

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
 * A list fragment representing a list of Movies. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link MovieDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class MovieListFragment extends Fragment implements constants,
        AdapterView.OnItemSelectedListener{

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks movieCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(Movie id) {

        }
    };
    // UI Elements
    private GridView gridView;
    // Logic variables
    private MovieCollection movieCollection;
    private String sortingCriteria;
    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = movieCallbacks;
    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = GridView.INVALID_POSITION;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializations
        movieCollection = new MovieCollection();
        sortingCriteria = "";

        //setListAdapter(new MovieThumbAdapter(getContext(), R.layout.movie_cell,
        //        movieCollection.getMovieCollection()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Spinner spinner;
        ArrayAdapter<CharSequence> sort_criteria_spinner_adapter;

        // Fragment's view
        View fragmentView = inflater.inflate(R.layout.activity_main, container, false);

        // <<============================ GridView initialization ================================>>
        gridView = (GridView) fragmentView.findViewById(R.id.grid_posters);
        // Associates the grid adapter with the movie collection ( a Movie object list)
        gridView.setAdapter(new MovieThumbAdapter(getActivity().getApplicationContext(),
                R.layout.movie_cell,
                movieCollection.getMovieCollection()));
        // If the user gets to the end of the UI, then more movies will be fetched
        gridView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                loadDataFromApi(page);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                //TODO remove this print
                System.out.println("AGARRA EL CLICK EN POSITION "+ position);
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mCallbacks.onItemSelected(MovieCollection.movieCollection.get(position));
            }
        });

        // <<============================ GridView initialization ================================>>

        // <<============================ Spinner initialization =================================>>
        spinner = (Spinner) fragmentView.findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        sort_criteria_spinner_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sort_criteria_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        sort_criteria_spinner_adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(sort_criteria_spinner_adapter);

        spinner.setOnItemSelectedListener(this);
        // <<============================ Spinner initialization =================================>>

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }

        if (savedInstanceState != null
                && savedInstanceState.get(MOVIE_LIST_KEY) != null){
            List<Movie> savedMovieList = (List<Movie>) savedInstanceState.get(MOVIE_LIST_KEY);
            sortingCriteria = savedInstanceState.getString(SORTING_CRITERIA_KEY);
            for (int i = 0; i < savedMovieList.size(); i++){
                movieCollection.addMovie(savedMovieList.get(i));
            }
            updateUI();
        }
        else {
            // Perform the first movie search to display initial options to the user
            loadDataFromApi(FIRST_PAGE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity){
            a=(Activity) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = movieCallbacks;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != GridView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
        outState.putParcelableArrayList(MOVIE_LIST_KEY,
                (ArrayList<? extends Parcelable>) movieCollection.getMovieCollection());
        outState.putString(SORTING_CRITERIA_KEY, sortingCriteria);
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, GridView will automatically
        // give items the 'activated' state when touched.
        gridView.setChoiceMode(activateOnItemClick
                ? GridView.CHOICE_MODE_SINGLE
                : GridView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == GridView.INVALID_POSITION) {
            gridView.setItemChecked(mActivatedPosition, false);
        } else {
            gridView.setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    /**
     * Resets the grid view adapter
     */
    private void resetGridAdapter() {
        movieCollection.clearMovieCollection();
        loadDataFromApi(FIRST_PAGE);
    }

    /**
     * Updates the gridView adapter
     */
    private void updateUI() {
        ((BaseAdapter) gridView.getAdapter()).notifyDataSetChanged();
    }

    /**
     * Selects the sorting criteria to display the movies from the options available
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String previousSortingCriteria = sortingCriteria;
        switch (position) {
            case 1:
                sortingCriteria = SORT_CRITERIA_RELEASE_DATE;
                break;
            case 2:
                sortingCriteria = SORT_CRITERIA_REVENUE;
                break;
            case 3:
                sortingCriteria = SORT_CRITERIA_HIGHEST_RATED;
                break;
            case 4:
                sortingCriteria = SORT_CRITERIA_MOST_POPULAR;
                break;
            case 5:
                sortingCriteria = SORT_CRITERIA_NAME;
                break;
            case 6: /* Favorites */
                sortingCriteria = SORT_CRITERIA_FAVORITES;
                loadFavoriteData();
                return;
            default:
                sortingCriteria = "";
                break;
        }
        // If the sorting criteria didn't change, there's nothing to be made
        if (!previousSortingCriteria.equals(sortingCriteria)){
            resetGridAdapter();
        }

    }

    /**
     * Method of OnItemSelectedListener from AdapterListener for the Spinner
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Creates the thread that loads favorite movies information from DataBase
     */
    private void loadFavoriteData() {
        new FavoritesCharge().execute();

    }

    /**
     * Loads movies information from THE MOVIE DATABASE web service using its API
     * It takes into account the sort criteria selected for the query
     * @param page
     */
    private void loadDataFromApi(int page) {
        QueryBuilder queryBuilder = new QueryBuilder();
        new TMDBConnection().execute(queryBuilder.getDiscoverQuery(page,sortingCriteria));
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        void onItemSelected(Movie id);
    }

    /**
     * Private class to charge favorite movies information from SQLite DB
     * The loading is done on a specific thread
     */
    private class FavoritesCharge extends AsyncTask {

        ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(LOADING_MOVIES);
            this.dialog.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            movieCollection.clearMovieCollection();

            MovieSelection movieSelection = new MovieSelection();
            movieSelection.count(getActivity().getContentResolver());
            String[] projection = { MovieColumns.TITLE, MovieColumns.RELEASEDATE, MovieColumns.IMAGE,
                    MovieColumns.RATING, MovieColumns.DESCRIPTION, MovieColumns._ID};
            MovieCursor movieCursor = movieSelection.query(getActivity().getContentResolver(), projection);
            movieCursor.moveToNext();
            for (int index = 0; index < movieCursor.getCount(); index++)
            {
                Movie movie = null;
                movie = new Movie(movieCursor.getTitle(),
                        movieCursor.getReleasedate(),
                        SORT_CRITERIA_FAVORITES,
                        Double.parseDouble(movieCursor.getRating()),
                        movieCursor.getDescription(),
                        ((int) movieCursor.getId())
                );
                movie.setPoster(movieCursor.getImage());
                movieCollection.addMovie(movie);
                movieCursor.moveToNext();
            }
            movieCursor.close();
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            updateUI();
        }
    }

    /**
     * Private class to charge movies information from THE MOVIE DB website using its API
     * The loading is done on a specific thread
     */
    private class TMDBConnection extends AsyncTask {

        ProgressDialog dialog = new ProgressDialog(getActivity());

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
                Log.e(APP_TAG, "STACKTRACE");
                Log.e(APP_TAG, Log.getStackTraceString(e));
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
                    movie.setVote_average(jsonMovieObject.getDouble("vote_average"));
                    movie.setId(jsonMovieObject.getInt("id"));
                    movieCollection.addMovie(movie);
                }
            } catch (JSONException e) {
                Log.e(APP_TAG, "STACKTRACE");
                Log.e(APP_TAG, Log.getStackTraceString(e));
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
