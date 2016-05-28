package Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TvShowsParser {

    String result;
    List<TvShowsBean> showsList = new ArrayList<>();

    public TvShowsParser(String result){
        this.result = result;
    }

    public List<TvShowsBean> getShowsList() throws Exception{

        if(result == null) {
            throw new Exception("No or poor internet connection");
        }
        try {
            JSONObject root = new JSONObject(result);
            JSONArray resultArray = root.getJSONArray("results");
            for(int i = 0; i < resultArray.length(); i++){
                TvShowsBean tvShowsBean = new TvShowsBean();
                JSONObject shows = resultArray.getJSONObject(i);
                String poster_path = shows.getString("poster_path");
                Float popularity = (float)shows.getDouble("popularity");
                int id = shows.getInt("id");
                String backdrop_path = shows.getString("backdrop_path");
                float vote_average = (float) shows.getDouble("vote_average");
                String overview = shows.getString("overview");
                String first_air_date = shows.getString("first_air_date");
                JSONArray origin_country = shows.getJSONArray("origin_country");
                String[] origin_country_catch = new String[origin_country.length()];
                for (int k = 0;k < origin_country.length();k++)
                    origin_country_catch[k]=origin_country.getString(k);
                JSONArray genre_ids = shows.getJSONArray("genre_ids");
                int genre_ids_catch[] = new int[genre_ids.length()];
                for(int j = 0; j < genre_ids.length(); j++)
                    genre_ids_catch[j] = genre_ids.getInt(j);
                String original_language = shows.getString("original_language");
                int vote_count = shows.getInt("vote_count");
                String name = shows.getString("name");
                String original_name = shows.getString("original_name");

                tvShowsBean.setPoster_path(poster_path);
                tvShowsBean.setPopularity(popularity);
                tvShowsBean.setId(id);
                tvShowsBean.setBackdrop_path(backdrop_path);
                tvShowsBean.setVote_average(vote_average);
                tvShowsBean.setOverview(overview);
                tvShowsBean.setFirst_air_date(first_air_date);
                tvShowsBean.setOrigin_country(origin_country_catch);
                tvShowsBean.setGenre_ids(genre_ids_catch);
                tvShowsBean.setName(name);
                tvShowsBean.setOriginal_language(original_language);
                tvShowsBean.setVote_count(vote_count);
                tvShowsBean.setOriginal_name(original_name);

                showsList.add(tvShowsBean);
            }
        }catch (JSONException Jex){
            Jex.printStackTrace();
            throw new Exception("Something went wrong! Try again later");
        }

        return showsList;
    }
}
