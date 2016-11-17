package com.cinemast.cinemast.utils;

import com.cinemast.cinemast.modals.ProfileDetailBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by suny on 13/5/16.
 */

public class ProfileDetailParser {
    String result;

    public ProfileDetailParser(String json) {
        this.result = json;
    }

    public ProfileDetailBean getProfileDetail() throws Exception{
        if(result == null)
            throw new Exception("No or poor internet connection");
        ProfileDetailBean bean = new ProfileDetailBean();
        try {
            JSONObject root = new JSONObject(result);
            boolean adult = false;
            if(root.has("adult")) {
                adult = root.getBoolean("adult");
            }
            ArrayList<String> list = new ArrayList<>();
            if(root.has("also_known_as")) {
                JSONArray alsoKnownAs = root.getJSONArray("also_known_as");
                for (int i = 0; i < alsoKnownAs.length(); i++) {
                    list.add(alsoKnownAs.getString(i));
                }
            }
            String biography = root.getString("biography");
            String birthday = root.getString("birthday");
            String deathday = root.getString("deathday");
            int gender = root.getInt("gender");
            String homepage = root.getString("homepage");
            int id = root.getInt("id");
            String imdb_id = root.getString("imdb_id");
            String name = root.getString("name");
            String place_of_birth = root.getString("place_of_birth");
            float popularity = (float)root.getDouble("popularity");
            String profile_path = root.getString("profile_path");

            bean.setAdult(adult);
            bean.setAlso_known_as(list);
            bean.setBiography(biography);
            bean.setBirthday(birthday);
            bean.setDeathday(deathday);
            bean.setGender(gender);
            bean.setHomepage(homepage);
            bean.setImdb_id(imdb_id);
            bean.setName(name);
            bean.setPopularity(popularity);
            bean.setPlace_of_birth(place_of_birth);
            bean.setProfile_path(profile_path);
            bean.setId(id);
        }catch (JSONException ex) {
            ex.printStackTrace();
            throw new JSONException("Something went wrong! Try again later");
        }
        return bean;
    }
}
