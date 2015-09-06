package com.sergiosaborio.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SergioSaborio on 9/6/15.
 * Reference: https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */
public class TrailerAdapter extends ArrayAdapter<Trailer> implements constants{

    Context context;

    public TrailerAdapter(Context context, ArrayList<Trailer> reviews) {
        super(context, 0, reviews);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Trailer trailer = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.trailer_cell, parent, false);
            // If a trailer is clicked, it can be played
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    trailerClicked(getItem(position).getKey());
                }
            });

            viewHolder.trailerName = (TextView) convertView.findViewById(R.id.textView_trailerName);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.trailerName.setText(trailer.getName());

        // Return the completed view to render on screen
        return convertView;

    }

    private void trailerClicked(String id){
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(BASE_YOUTUBE_APP_URL + id));
            context.startActivity(intent);
        }catch (ActivityNotFoundException ex){
            Intent intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse(BASE_YOUTUBE_URL+id));
            context.startActivity(intent);
        }
        Log.i("Video", "Video Playing....");
    }

    // View lookup cache
    private static class ViewHolder {
        TextView trailerName;
    }

}