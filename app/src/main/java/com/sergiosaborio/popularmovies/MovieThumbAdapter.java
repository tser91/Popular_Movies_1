package com.sergiosaborio.popularmovies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SergioSaborio on 8/24/15.
 * Reference: http://stackoverflow.com/questions/28544283/
 * android-how-to-set-the-imageview-src-in-a-list-dynamically
 */
public class MovieThumbAdapter extends BaseAdapter implements constants {

    Context context;
    List<Movie> movieList;

    public MovieThumbAdapter(Context context, int resource, List<Movie> objects) {

        this.context = context;
        this.movieList = objects;
    }

    @Override
    public int getCount() {
        if(movieList != null)
            return movieList.size();
        return 0;
    }

    @Override
    public Movie getItem(int position) {
        if(movieList != null)
            return movieList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(movieList != null)
            return movieList.get(position).hashCode();
        return 0;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder;

        //If the gridview does not have an xml layout ready set the layout
        if (convertView == null){

            //we need a new holder to hold the structure of the cell
            holder = new Holder();

            //get the XML inflation service
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);

            //Inflate our xml cell to the convertView
            convertView = inflater.inflate(R.layout.movie_cell, null);

            //Get xml components into our holder class
            holder.imageView = (ImageView)convertView.findViewById(R.id.movie_cell_imageview);

            //Attach our holder class to this particular cell
            convertView.setTag(holder);

        }else{

            //The gridview cell is not empty and contains already components loaded,
            // get the tagged holder
            holder = (Holder)convertView.getTag();
        }

        //Fill our cell with data

        //get our person object from the list we passed to the adapter
        Movie movie = getItem(position);


        //Fill our view components with data
        // Check if there is no poster available for the image
        if (movie.getMovie_poster_url().equals("null")) {
            Picasso.with(context).load(IMAGE_NOT_AVAILABLE_URL).fit().into(holder.imageView);
        }
        else if (movie.getMovie_poster_url().equals(SORT_CRITERIA_FAVORITES))
        {
            byte[] bytes = movie.getPoster();
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            holder.imageView.setImageBitmap(bmp);
        }
        else {
            Picasso.with(context).load(BASE_IMAGE_URL +
                    movie.getMovie_poster_url()).fit().into(holder.imageView);
        }

        return convertView;
    }

    /**
     * This holder must replicate the components in the person_cell.xml
     * We have a textview for the name and the surname and an imageview for the picture
     */
    private class Holder{
        ImageView imageView;
    }
}
