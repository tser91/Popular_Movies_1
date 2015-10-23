package com.sergiosaborio.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SergioSaborio on 9/5/15.
 * Reference: https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */
public class Review implements Parcelable {

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
    private String id;
    private String author;
    private String content;

    public Review(String id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public Review() {
        this.id = "";
        this.author = "";
        this.content = "";
    }

    // Constructor to convert JSON object into a Java class instance
    public Review(JSONObject object){
        try {
            this.id = object.getString("id");
            this.author = object.getString("author");
            this.content = object.getString("content");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected Review(Parcel in) {
        id = in.readString();
        author = in.readString();
        content = in.readString();
    }

    // Factory method to convert an array of JSON objects into a list of objects
    // Review.fromJson(jsonArray);
    public static ArrayList<Review> fromJson(JSONArray jsonObjects) {
        ArrayList<Review> reviews = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                reviews.add(new Review(jsonObjects.getJSONObject(i)));
                System.out.println("Review "+ i+ ": author -> " + reviews.get(i).getAuthor());
                System.out.println("Review "+ i+ ": content -> " + reviews.get(i).getContent());
                System.out.println("Review " + i + ": id -> " + reviews.get(i).getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return reviews;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(content);
    }
}
