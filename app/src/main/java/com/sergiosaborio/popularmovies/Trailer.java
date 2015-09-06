package com.sergiosaborio.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SergioSaborio on 9/6/15.
 */
public class Trailer {

    private String key;
    private String name;
    private String id;

    public Trailer(String key, String name, String id) {
        this.key = key;
        this.name = name;
        this.id = id;
    }

    public Trailer() {
        this.key = "";
        this.name = "";
        this.id = "";
    }

    // Constructor to convert JSON object into a Java class instance
    public Trailer(JSONObject object){
        try {
            this.id = object.getString("id");
            this.key = object.getString("key");
            this.name = object.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Factory method to convert an array of JSON objects into a list of objects
    public static ArrayList<Trailer> fromJson(JSONArray jsonObjects) {
        ArrayList<Trailer> trailers = new ArrayList<Trailer>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                trailers.add(new Trailer(jsonObjects.getJSONObject(i)));
                System.out.println("Trailer "+ i+ ": name -> " + trailers.get(i).getName());
                System.out.println("Trailer "+ i+ ": key -> " + trailers.get(i).getKey());
                System.out.println("Trailer " + i + ": id -> " + trailers.get(i).getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return trailers;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
