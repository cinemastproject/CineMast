package Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PopularMovieParser {

    String result;
    List<PopularMovieBean> moviesList = new ArrayList<>();

    public PopularMovieParser(String result){
        this.result = result;
    }

    public List<PopularMovieBean> getMoviesList() throws Exception{

        if(result == null)
            throw new Exception("No or poor internet connection");

        try {
            JSONObject root = new JSONObject(result);
            JSONArray resultArray = root.getJSONArray("results");
            for(int i = 0; i < resultArray.length(); i++){
                PopularMovieBean popularMovieBean = new PopularMovieBean();
                JSONObject movie = resultArray.getJSONObject(i);
                String poster_path = movie.getString("poster_path");
                Boolean adult = movie.getBoolean("adult");
                String overview = movie.getString("overview");
                String release_date = movie.getString("release_date");
                JSONArray genre_ids = movie.getJSONArray("genre_ids");
                int genre_ids_catch[] = new int[genre_ids.length()];
                for(int j = 0; j < genre_ids.length(); j++)
                    genre_ids_catch[j] = genre_ids.getInt(j);
                int id = movie.getInt("id");
                String original_title = movie.getString("original_title");
                String original_language = movie.getString("original_language");
                String title = movie.getString("title");
                String backdrop_path = movie.getString("backdrop_path");
                float popularity = (float) movie.getDouble("popularity");
                int vote_count = movie.getInt("vote_count");
                Boolean video = movie.getBoolean("video");
                float vote_average = (float) movie.getDouble("vote_average");

                popularMovieBean.setPoster_path(poster_path);
                popularMovieBean.setAdult(adult);
                popularMovieBean.setOverview(overview);
                popularMovieBean.setRelease_date(release_date);
                popularMovieBean.setGenre_ids(genre_ids_catch);
                popularMovieBean.setId(id);
                popularMovieBean.setOriginal_title(original_title);
                popularMovieBean.setOriginal_language(original_language);
                popularMovieBean.setTitle(title);
                popularMovieBean.setBackdrop_path(backdrop_path);
                popularMovieBean.setPopularity(popularity);
                popularMovieBean.setVote_count(vote_count);
                popularMovieBean.setVideo(video);
                popularMovieBean.setVote_average(vote_average);
                moviesList.add(popularMovieBean);
            }
        }catch (JSONException Jex){
            Jex.printStackTrace();
            throw new Exception("Something went wrong! Try again later");
        }

        return moviesList;
    }
}
