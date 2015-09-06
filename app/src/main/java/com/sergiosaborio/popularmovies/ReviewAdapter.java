package com.sergiosaborio.popularmovies;

import android.content.Context;
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
public class ReviewAdapter extends ArrayAdapter<Review> {

    public ReviewAdapter(Context context, ArrayList<Review> reviews) {
        super(context, 0, reviews);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Review review = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.review_cell, parent, false);
            viewHolder.reviewer = (TextView) convertView.findViewById(R.id.textview_reviewer);
            viewHolder.review = (TextView) convertView.findViewById(R.id.textview_review);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.reviewer.setText(review.getAuthor());
        viewHolder.review.setText(review.getContent());
        // Return the completed view to render on screen
        return convertView;

    }

    // View lookup cache
    private static class ViewHolder {
        TextView reviewer;
        TextView review;
    }
}