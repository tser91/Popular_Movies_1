package com.sergiosaborio.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SergioSaborio on 9/5/15.
 * Reference: https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */
public class Review {

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

    // Factory method to convert an array of JSON objects into a list of objects
    // Review.fromJson(jsonArray);
    public static ArrayList<Review> fromJson(JSONArray jsonObjects) {
        ArrayList<Review> reviews = new ArrayList<Review>();
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
}
