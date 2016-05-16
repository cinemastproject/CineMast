package Utilities;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TVShowDetailParser {
    String json;
    List<TVShowDetailBean> tvShowDetailList = new ArrayList<>();
    List<CreatedByDetail> createdByDetailsList = new ArrayList<>();
    List<GenreDetail> genreDetailList = new ArrayList<>();
    List<NetworkDetail> networkDetailList = new ArrayList<>();
    List<ProductionCompanyDetail> productionCompanyDetailList = new ArrayList<>();
    ArrayList<TVShowDetailBean.Season> seasonList = new ArrayList<>();
    TVShowDetailBean tvShowDetailBean = new TVShowDetailBean();

    public TVShowDetailParser(String json){
        this.json = json;
    }

    public TVShowDetailBean getTvShowDetail() throws Exception{
        try {
            JSONObject root = new JSONObject(json);
            String backdrop_path = root.getString("backdrop_path");
            JSONArray createdByArray = root.getJSONArray("created_by");
            for (int i = 0; i < createdByArray.length(); i++){
                CreatedByDetail createdByDetail = new CreatedByDetail();
                JSONObject createdBy = createdByArray.getJSONObject(i);
                String id = createdBy.getString("id");
                String name = createdBy.getString("name");
                String profile_path = createdBy.getString("profile_path");
                createdByDetail.setId(id);
                createdByDetail.setName(name);
                createdByDetail.setProfile_path(profile_path);
                createdByDetailsList.add(createdByDetail);
            }
            JSONArray episode_run_timeArray = root.getJSONArray("episode_run_time");
            int[] episode_run_time = new int[episode_run_timeArray.length()];
            for (int i = 0; i < episode_run_timeArray.length(); i++) {
                episode_run_time[i] = episode_run_timeArray.getInt(i);
            }
            String first_air_date = root.getString("first_air_date");
            JSONArray genreArray = root.getJSONArray("genres");
            for (int i = 0; i < genreArray.length(); i++){
                GenreDetail genreDetail = new GenreDetail();
                JSONObject genre = genreArray.getJSONObject(i);
                String id = genre.getString("id");
                String name = genre.getString("name");
                genreDetail.setId(id);
                genreDetail.setName(name);
                genreDetailList.add(genreDetail);
            }
            String homepage = root.getString("homepage");
            String id = root.getString("id");
            boolean in_production = root.getBoolean("in_production");
            JSONArray languageArray = root.getJSONArray("languages");
            String[] languages = new String[languageArray.length()];
            for (int i = 0; i < languageArray.length(); i++) {
                languages[i] = languageArray.getString(i);
            }
            String last_air_date = root.getString("last_air_date");
            String name = root.getString("name");
            JSONArray networksArray = root.getJSONArray("networks");
            for (int i = 0; i < networksArray.length(); i++){
                NetworkDetail networkDetail = new NetworkDetail();
                JSONObject networks = genreArray.getJSONObject(i);
                String nid = networks.getString("id");
                String nname = networks.getString("name");
                networkDetail.setId(nid);
                networkDetail.setName(nname);
                networkDetailList.add(networkDetail);
            }
            int number_of_episodes = root.getInt("number_of_episodes");
            int number_of_seasons = root.getInt("number_of_seasons");
            JSONArray origin_countryArray = root.getJSONArray("origin_country");
            String[] origin_country = new String[origin_countryArray.length()];
            for (int i = 0; i < origin_countryArray.length(); i++) {
                origin_country[i] = origin_countryArray.getString(i);
            }
            String original_language = root.getString("original_language");
            String original_name = root.getString("original_name");
            String overview = root.getString("overview");
            float popularity = (float) root.getDouble("popularity");
            String poster_path = root.getString("poster_path");
            JSONArray production_companiesArray = root.getJSONArray("production_companies");
            for (int i = 0; i < production_companiesArray.length(); i++){
                ProductionCompanyDetail productionCompanyDetail = new ProductionCompanyDetail();
                JSONObject production_companies = production_companiesArray.getJSONObject(i);
                String pid = production_companies.getString("id");
                String pname = production_companies.getString("name");
                productionCompanyDetail.setId(pid);
                productionCompanyDetail.setName(pname);
                productionCompanyDetailList.add(productionCompanyDetail);
            }
            JSONArray seasonsArray = root.getJSONArray("seasons");
            for (int i = 0; i < seasonsArray.length(); i++){
                TVShowDetailBean.Season seasonBean = tvShowDetailBean.new Season();
                JSONObject season = seasonsArray.getJSONObject(i);
                String air_date = season.getString("air_date");
                int episode_count = season.getInt("episode_count");
                String sid = season.getString("id");
                String season_poster_path = season.getString("poster_path");
                int season_number = season.getInt("season_number");
                seasonBean.setAir_date(air_date);
                seasonBean.setEpisode_count(episode_count);
                seasonBean.setId(sid);
                seasonBean.setPoster_path(season_poster_path);
                seasonBean.setSeason_number(season_number);
                seasonList.add(seasonBean);
            }
            String status = root.getString("status");
            String type = root.getString("type");
            float vote_average = (float) root.getDouble("vote_average");
            int vote_count = root.getInt("vote_count");
            tvShowDetailBean.setBackdrop_path(backdrop_path);
            tvShowDetailBean.setCreatedBy(createdByDetailsList);
            tvShowDetailBean.setEpisode_run_time(episode_run_time);
            tvShowDetailBean.setFirst_air_date(first_air_date);
            tvShowDetailBean.setGenreDetails(genreDetailList);
            tvShowDetailBean.setHomepage(homepage);
            tvShowDetailBean.setId(id);
            tvShowDetailBean.setIn_production(in_production);
            tvShowDetailBean.setLanguages(languages);
            tvShowDetailBean.setLast_air_date(last_air_date);
            tvShowDetailBean.setName(name);
            tvShowDetailBean.setNetworkDetails(networkDetailList);
            tvShowDetailBean.setNumber_of_episodes(number_of_episodes);
            tvShowDetailBean.setNumber_of_seasons(number_of_seasons);
            tvShowDetailBean.setOrigin_country(origin_country);
            tvShowDetailBean.setOriginal_language(original_language);
            tvShowDetailBean.setOriginal_name(original_name);
            tvShowDetailBean.setOverview(overview);
            tvShowDetailBean.setPopularity(popularity);
            tvShowDetailBean.setPoster_path(poster_path);
            tvShowDetailBean.setCompanyDetails(productionCompanyDetailList);
            tvShowDetailBean.setSeasons(seasonList);
            tvShowDetailBean.setStatus(status);
            tvShowDetailBean.setType(type);
            tvShowDetailBean.setVote_average(vote_average);
            tvShowDetailBean.setVote_count(vote_count);
            tvShowDetailList.add(tvShowDetailBean);
        }catch (JSONException jex){

        }
        return tvShowDetailBean;
    }
}
