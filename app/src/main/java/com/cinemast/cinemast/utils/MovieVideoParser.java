package com.cinemast.cinemast.utils;

import com.cinemast.cinemast.modals.MovieVideosBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieVideoParser {

    List<MovieVideosBean> movieVideoList = new ArrayList<>();
    String json;

    public MovieVideoParser(String json) {
        super();
        this.json = json;
    }

    public List<MovieVideosBean> getMovieVideoList() throws Exception{
        try{
            JSONObject root = new JSONObject(json);
            String yid = root.getString("id");
            JSONArray results = root.getJSONArray("results");
            for(int i = 0;i < results.length(); i++){
                MovieVideosBean bean = new MovieVideosBean();
                JSONObject result = results.getJSONObject(i);
                String id = result.getString("id");
                String key = result.getString("key");
                String name = result.getString("name");
                String site = result.getString("site");
                int size = result.getInt("size");
                String type = result.getString("type");

                bean.setId(id);
                bean.setKey(key);
                bean.setName(name);
                bean.setSite(site);
                bean.setSize(size);
                bean.setType(type);
                bean.setYid(yid);
                movieVideoList.add(bean);
            }
        }catch (JSONException ex) {
            throw new Exception("Something wrong with server");
        }
        return movieVideoList;
    }
}
