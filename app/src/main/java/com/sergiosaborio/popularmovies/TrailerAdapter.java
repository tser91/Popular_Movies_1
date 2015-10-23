package com.sergiosaborio.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SergioSaborio on 9/6/15.
 * Reference: https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */
public class TrailerAdapter extends BaseAdapter implements constants{

    Context context;
    List<Trailer> trailerList;

    public TrailerAdapter(Context context, List<Trailer> objects) {

        this.context = context;
        this.trailerList = objects;
    }

    @Override
    public int getCount() {
        if(trailerList != null)
            return trailerList.size();
        return 0;
    }

    @Override
    public Trailer getItem(int position) {
        if(trailerList != null)
            return trailerList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(trailerList != null)
            return trailerList.get(position).hashCode();
        return 0;

    }

    private void printTrailerList() {
        for (int i = 0; i < this.trailerList.size(); i++) {
            System.out.println("Trailer in position " + i +
                    "is " + this.trailerList.get(i).getId());
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            //get the XML inflation service
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);

            //Inflate our xml cell to the convertView
            convertView = inflater.inflate(R.layout.trailer_cell, null);

            // If a movie is clicked, the details will be shown
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    trailerClicked(getItem(position).getKey());
                }
            });

            //Get xml components into our holder class
            viewHolder.trailerName = (TextView) convertView.findViewById(R.id.textView_trailerName);

            //Attach our holder class to this particular cell
            convertView.setTag(viewHolder);
        }
        else {

            //The gridview cell is not empty and contains already components loaded,
            // get the tagged holder
            viewHolder = (ViewHolder)convertView.getTag();

            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    trailerClicked(getItem(position).getKey());
                }
            });

        }

        // Get the data item for this position
        Trailer trailer = getItem(position);
        // Populate the data into the template view using the data object
        viewHolder.trailerName.setText(trailer.getName());

        // Return the completed view to render on screen
        return convertView;

    }

    private void trailerClicked(String id){
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(BASE_YOUTUBE_APP_URL + id));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }catch (ActivityNotFoundException ex){
            Intent intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse(BASE_YOUTUBE_URL+id));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        Log.i("Video", "Video Playing....");
    }

    // View lookup cache
    private static class ViewHolder {
        TextView trailerName;
    }

}