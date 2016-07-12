package com.example.scs_user.jsonflickr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SCS_USER on 7/8/2016.
 */
public class FlickrPhoto extends Object {
    String id;
    String owner;
    String secret;
    String server;
    String farm;
    String title;
    Boolean isPublic;
    Boolean isFriend;
    Boolean isFamily;
    public FlickrPhoto(JSONObject jsonPhoto) throws JSONException{
        this.id = jsonPhoto.optString("id");
        this.owner = jsonPhoto.optString("owner");
        this.secret = jsonPhoto.optString("secret");
        this.server = jsonPhoto.optString("server");
        this.farm = jsonPhoto.optString("farm");
        this.title = jsonPhoto.optString("title");
        this.isPublic = jsonPhoto.optBoolean("ispublic");
        this.isFamily = jsonPhoto.optBoolean("isfamily");
        this.isFriend = jsonPhoto.optBoolean("isfriend");
    }
    public static ArrayList<FlickrPhoto> makePhotolist(String photodata) throws JSONException,NullPointerException{
        ArrayList<FlickrPhoto> FlickrPhotos = new ArrayList<>();
        JSONObject data = new JSONObject(photodata);
        JSONObject photos = data.optJSONObject("photos");
        JSONArray photoArray = photos.optJSONArray("photo");
        for (int i=0;i<photoArray.length();i++ )
        {
            JSONObject photo = (JSONObject) photoArray.get(i);
            FlickrPhoto currentPhoto = new FlickrPhoto(photo);
            FlickrPhotos.add(currentPhoto);
        }
        return FlickrPhotos;
    }
}
