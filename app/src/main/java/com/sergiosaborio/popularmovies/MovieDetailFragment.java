package com.sergiosaborio.popularmovies;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sergiosaborio.popularmovies.provider.movie.MovieColumns;
import com.sergiosaborio.popularmovies.provider.movie.MovieContentValues;
import com.sergiosaborio.popularmovies.provider.movie.MovieCursor;
import com.sergiosaborio.popularmovies.provider.movie.MovieSelection;
import com.sergiosaborio.popularmovies.provider.review.ReviewColumns;
import com.sergiosaborio.popularmovies.provider.review.ReviewContentValues;
import com.sergiosaborio.popularmovies.provider.review.ReviewCursor;
import com.sergiosaborio.popularmovies.provider.review.ReviewSelection;
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

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment implements constants {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The movie content this fragment is presenting.
     */
    private Movie movie;

    /**
     * The list view of the trailers of the movie this fragment is representing
     */
    private ListView listViewTrailers;

    /**
     * The list view of the reviews of the movie this fragment is representing
     */
    private ListView listViewReviews;

    /**
     * The trailers of the movie this fragment is representing
     */
    private ArrayList<Trailer> trailersArray;

    /**
     * The reviews of the movie this fragment is representing
     */
    private ArrayList<Review> reviewsArray;

    /**
     * Indicates if the movie is set as favorite
     */
    private boolean isMovieFavorite;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Indicates that there is a menu option proper of the fragment
        setHasOptionsMenu(true);

        // Initializations
        trailersArray = new ArrayList<>();
        reviewsArray = new ArrayList<>();


        // Get the arguments sent to this fragment
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            movie = getArguments().getParcelable(ARG_ITEM_ID);
        }

        isMovieFavorite = isMovieFavorite();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        if (movie != null) {
            // Create the adapter to convert the array to views
            // Attach the adapter to a ListView
            listViewTrailers = (ListView) rootView.findViewById(R.id.listView_movie_trailers);
            listViewTrailers.setAdapter(new TrailerAdapter(getActivity().getApplicationContext(),
                    trailersArray));

            listViewTrailers.setClickable(true);
            listViewTrailers.setItemsCanFocus(false);

            listViewReviews = (ListView) rootView.findViewById(R.id.listView_movie_reviews);
            listViewReviews.setAdapter(new ReviewAdapter(getActivity().getApplicationContext(),
                    reviewsArray));

            listViewReviews.setClickable(true);
            listViewReviews.setItemsCanFocus(false);

            final ImageButton button = (ImageButton) rootView.findViewById(R.id.button_favorite);
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
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Update the UI with the movie's information
        updateUI();

        if (savedInstanceState != null){
            updateAdapters();
        }
        else {
            // Check if movie is favorite, then load adapters from DB
            if (isMovieFavorite) {
                ImageButton button = (ImageButton) view.findViewById(R.id.button_favorite);
                new TrailersCharge().execute();
                new ReviewsCharge().execute();
                button.setImageResource(android.R.drawable.btn_star_big_on);
                button.setSelected(true);
            }
            else {
                loadDataFromApi(FIRST_PAGE);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Menu option for sharing the first movie trailer
            case R.id.share:
                shareTrailer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Check if movie is marked as favorite
     * @return
     */
    @DebugLog
    private boolean isMovieFavorite() {
        boolean result = false;
        MovieSelection movieSelection = new MovieSelection();
        movieSelection.title(movie.getTitle());
        String[] projection = { MovieColumns._ID};
        MovieCursor movieCursor = movieSelection.query(getActivity().getContentResolver(),
                projection);
        movieCursor.moveToNext();

        result = movieCursor.getCount() > 0;

        movieCursor.close();
        return result;
    }

    // ============================== Remove from DB ==============================>

    /**
     * Remove reviews of a movie from the DB
     */
    private void removeReviews() {
        MovieSelection movieSelection = new MovieSelection();
        movieSelection.title(movie.getTitle());
        String[] projection = { MovieColumns._ID};
        MovieCursor movieCursor = movieSelection.query(getActivity().getContentResolver(),
                projection);
        movieCursor.moveToNext();

        ReviewSelection reviewSelection = new ReviewSelection();
        reviewSelection.movieId(movieCursor.getId());
        reviewSelection.delete(getActivity().getContentResolver());

        movieCursor.close();
    }

    /**
     * Remove trailers of a movie from the DB
     */
    private void removeTrailers() {
        MovieSelection movieSelection = new MovieSelection();
        movieSelection.title(movie.getTitle());
        String[] projection = { MovieColumns._ID};
        MovieCursor movieCursor = movieSelection.query(getActivity().getContentResolver(),
                projection);
        movieCursor.moveToNext();

        TrailerSelection trailerSelection = new TrailerSelection();
        trailerSelection.movieId(movieCursor.getId());
        trailerSelection.delete(getActivity().getContentResolver());

        movieCursor.close();
    }

    /**
     * Remove a movie from the DB
     */
    private void removeMovie() {
        MovieSelection movieSelection = new MovieSelection();
        movieSelection.title(movie.getTitle());
        movieSelection.delete(getActivity().getContentResolver());
    }

    // <============================== Remove from DB ==============================


    // ============================== Insert into DB ==============================>

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
            Uri uri = trailerValues.insert(getActivity().getContentResolver());
            ContentUris.parseId(uri);
        }
    }

    /**
     * Insert a reviews of the movie.
     *
     */
    @DebugLog
    private void insertReviews(long movieID) {
        for (int index = 0; index < reviewsArray.size(); index++)
        {
            ReviewContentValues reviewValues = new ReviewContentValues();
            reviewValues.putName(reviewsArray.get(index).getAuthor());
            reviewValues.putReview(reviewsArray.get(index).getContent());
            reviewValues.putMovieId(movieID);
            Uri uri = reviewValues.insert(getActivity().getContentResolver());
            ContentUris.parseId(uri);
        }
    }
    
    /**
     * Insert a movie
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
        Uri uri = movieValues.insert(getActivity().getContentResolver());
        return ContentUris.parseId(uri);
    }

    // <============================== Insert into DB ==============================


    /**
     * Turns image form into a byte array to be displayed if no internet
     * @return
     */
    private byte[] getMovieImage() {
        // convert View into Bitmap
        ImageView view = (ImageView) getActivity().findViewById(R.id.imageView_movied);
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bm = view.getDrawingCache();

        // convert Bitmap* into ByteArray
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    /**
     * Updates the UI elements using the movie information
     */
    private void updateUI() {
        TextView textview;

        // Update movie poster
        Context context = getActivity().getApplicationContext();
        ImageView image = (ImageView) getActivity().findViewById(R.id.imageView_movied);
        image.setMaxHeight((int) (image.getWidth() / HEIGHT_PROPORTION));
        // Check if there is no poster available for the image
        if (isMovieFavorite)
        {
            MovieSelection movieSelection = new MovieSelection();
            movieSelection.title(movie.getTitle());
            String[] projection = { MovieColumns.IMAGE};
            MovieCursor movieCursor = movieSelection.query(getActivity().getContentResolver(),
                    projection);
            movieCursor.moveToNext();

            byte[] bytes = movieCursor.getImage();
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ((ImageView) getActivity().findViewById(R.id.imageView_movied)).setImageBitmap(bmp);
            movieCursor.close();
        }
        else if (movie.getMovie_poster_url().equals("null")) {
            Picasso.with(context).load(IMAGE_NOT_AVAILABLE_URL).fit().into(
                    (ImageView) getActivity().findViewById(R.id.imageView_movied));
        }
        else {
            Picasso.with(context).load(BASE_IMAGE_URL +
                    movie.getMovie_poster_url()).fit().into(
                    (ImageView) getActivity().findViewById(R.id.imageView_movied));
        }

        //Update movie title
        textview = (TextView) getActivity().findViewById(R.id.textView_movied_title);
        if (movie.getTitle().equals("null")) {
            textview.setText(TITLE_NOT_AVAILABLE);
        } else {
            textview.setText(movie.getTitle());
        }

        //Update movie release date
        textview = (TextView) getActivity().findViewById(R.id.textView_movied_releaseDate);
        if (movie.getRelease_date().equals("null")) {
            textview.setText(RELEASE_DATE_NOT_AVAILABLE);
        } else {
            textview.setText(movie.getRelease_date());
        }

        //Update movie plot
        textview = (TextView) getActivity().findViewById(R.id.textView_movied_plot);
        if (movie.getPlot_synopsis().equals("null")) {
            textview.setText(DESCRIPTION_NOT_AVAILABLE);
        } else {
            textview.setText(movie.getPlot_synopsis());
        }

        //Update movie rating
        textview = (TextView) getActivity().findViewById(R.id.textView_moviedb_rating);
        if ((movie.getVote_average() + "").equals("null")) {
            textview.setText(RATING_NOT_AVAILABLE);
        } else {
            textview.setText(movie.getVote_average() + TOTAL_RATING);
        }
    }

    /**
     * Sharing the first trailer of the movie.
     * First try to share using youtube app if installed, if not use the browser
     */
    private void shareTrailer() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Share " + movie.getTitle() + " trailer" );
        share.putExtra(Intent.EXTRA_TEXT, BASE_YOUTUBE_URL + trailersArray.get(0).getKey());

        startActivity(Intent.createChooser(share, "Share link!"));

    }

    /**
     * Starts the threads in charge of getting the trailers and reviews info of a movie
     * @param page
     */
    private void loadDataFromApi(int page) {
        new TMDBConnection().execute(new QueryBuilder().getMovieReviews(page, movie.getId()),
                REVIEWS_QUERY);
        new TMDBConnection().execute(new QueryBuilder().getMovieTrailers(page, movie.getId()),
                VIDEOS_QUERY);
    }

    /**
     * Gets the trailers information of a movie, out of a JSON string
     * @param jsonInfo
     */
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

    /**
     * Gets the reviews of a movie out of a JSON string
     * @param jsonInfo
     */
    private void getMovieReviews(String jsonInfo){

        try {
            JSONObject jsonObject = new JSONObject(jsonInfo);
            JSONArray array = (JSONArray) jsonObject.get("results");
            ArrayList<Review> reviews = Review.fromJson(array);
            for (int i = 0; i < reviews.size(); i++)
            {
                reviewsArray.add(reviews.get(i));
            }
        } catch (JSONException e) {
            Log.e(APP_TAG, "STACKTRACE");
            Log.e(APP_TAG, Log.getStackTraceString(e));
        }

    }

    /**
     * Updates the adapters of reviews and trailers of the movie (UI)
     */
    private void updateAdapters() {
        ((BaseAdapter) listViewTrailers.getAdapter()).notifyDataSetChanged();
        ((BaseAdapter) listViewReviews.getAdapter()).notifyDataSetChanged();
    }


    // ============================== Private Threads ==============================>

    /**
     * Private class to create a thread in charge of downloading the trailers and reviews
     * info of a movie from the TheMovieDB web service using its API
     */
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

    /**
     * Private class to create a thread in charge of getting the trailer's information of a
     * movie from the DB
     */
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
            MovieCursor movieCursor = movieSelection.query(getActivity().getContentResolver(),
                    movieProjection);
            movieCursor.moveToNext();

            // Get the trailers of the movie
            TrailerSelection trailerSelection = new TrailerSelection();
            trailerSelection.movieId(movieCursor.getId());

            movieCursor.close();

            String[] trailerProjection = {TrailerColumns._ID, TrailerColumns.NAME,
                    TrailerColumns.URL, TrailerColumns.MOVIE_ID};
            TrailerCursor trailerCursor = trailerSelection.query(getActivity().getContentResolver(),
                    trailerProjection);
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

    /**
     * Private class to create a thread in charge of getting the trailer's information of a
     * movie from the DB
     */
    private class ReviewsCharge extends AsyncTask {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Object doInBackground(Object[] params) {
            reviewsArray.clear();

            // Get movie ID from DB
            MovieSelection movieSelection = new MovieSelection();
            movieSelection.title(movie.getTitle());
            String[] movieProjection = { MovieColumns._ID};
            MovieCursor movieCursor = movieSelection.query(getActivity().getContentResolver(),
                    movieProjection);
            movieCursor.moveToNext();

            // Get the reviews of the movie
            ReviewSelection reviewSelection = new ReviewSelection();
            reviewSelection.movieId(movieCursor.getId());

            movieCursor.close();

            String[] reviewProjection = {ReviewColumns._ID, ReviewColumns.NAME,
                    ReviewColumns.REVIEW, ReviewColumns.MOVIE_ID};
            ReviewCursor reviewCursor = reviewSelection.query(getActivity().getContentResolver(),
                    reviewProjection);
            reviewCursor.moveToNext();

            for (int index = 0; index < reviewCursor.getCount(); index++)
            {
                Review review = new Review(String.valueOf(reviewCursor.getMovieId()),
                        reviewCursor.getName(), reviewCursor.getReview());
                reviewsArray.add(review);
                reviewCursor.moveToNext();
            }
            reviewCursor.close();
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            updateAdapters();
        }
    }

    // <============================== Private Threads ==============================

}
