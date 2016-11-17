package com.cinemast.cinemast.utils;

import com.cinemast.cinemast.modals.MovieDetailsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailsParser {
    String result;

    public MovieDetailsParser(String result){
        this.result = result;
    }

    public MovieDetailsBean getMovieDetails(){
        MovieDetailsBean movieDetailsBean = new MovieDetailsBean();
        try {
            JSONObject root = new JSONObject(result);
            boolean adult = root.getBoolean("adult");
            String backdrop_path = root.getString("backdrop_path");
            JSONArray genresList = root.getJSONArray("genres");
            String[] genreArray = new String[genresList.length()];
            for (int i = 0; i < genresList.length(); i++){
                JSONObject genre = genresList.getJSONObject(i);
                genreArray[i] = genre.getString("name");
            }
            String homepage = root.getString("homepage");
            String id = root.getString("id");
            String imdb_id = root.getString("imdb_id");
            String original_language = root.getString("original_language");
            String original_title = root.getString("original_title");
            String overview = root.getString("overview");
            double popularity = root.getDouble("popularity");
            String poster_path = root.getString("poster_path");
            String release_date = root.getString("release_date");
            int runtime = root.getInt("runtime");
            String status = root.getString("status");
            String tagline = root.getString("tagline");
            String title = root.getString("title");
            boolean video = root.getBoolean("video");
            int vote_average = root.getInt("vote_average");
            int vote_count = root.getInt("vote_count");

            movieDetailsBean.setAdult(adult);
            movieDetailsBean.setBackdrop_path(backdrop_path);
            movieDetailsBean.setGenres(genreArray);
            movieDetailsBean.setHomepage(homepage);
            movieDetailsBean.setId(id);
            movieDetailsBean.setImdb_id(imdb_id);
            movieDetailsBean.setOriginal_language(original_language);
            movieDetailsBean.setOriginal_title(original_title);
            movieDetailsBean.setOverview(overview);
            movieDetailsBean.setPopularity(popularity);
            movieDetailsBean.setPoster_path(poster_path);
            movieDetailsBean.setRelease_date(release_date);
            movieDetailsBean.setRuntime(runtime);
            movieDetailsBean.setStatus(status);
            movieDetailsBean.setTagline(tagline);
            movieDetailsBean.setTitle(title);
            movieDetailsBean.setVideo(video);
            movieDetailsBean.setVote_average(vote_average);
            movieDetailsBean.setVote_count(vote_count);

        }catch (JSONException jex){

        }
        return movieDetailsBean;

    }

}
