package Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SeasonDetailsParser {
    List<SeasonDetailsBean> seasonDetailsList = new ArrayList<>();
    List<SeasonDetailsBean.Episode> episodeList = new ArrayList<>();
    List<CrewDetails> crewDetailsList = new ArrayList<>();
    List<GuestStarsDetails> guestStarsDetailsList = new ArrayList<>();
    String json;

    public SeasonDetailsParser(String json){
        super();
        this.json = json;
    }

    public List<SeasonDetailsBean> getSeasonDetails() throws Exception{
        try{
            JSONObject root = new JSONObject(json);
            SeasonDetailsBean seasonDetailsBean = new SeasonDetailsBean();
            String air_date = root.getString("air_date");
            JSONArray episode = root.getJSONArray("episode");
            for(int i = 0; i < episode.length(); i++){
                SeasonDetailsBean.Episode episodeBean = seasonDetailsBean.new Episode();
                JSONObject episodeNum = episode.getJSONObject(i);
                String episode_air_date = episodeNum.getString("air_date");
                JSONArray crewList = episodeNum.getJSONArray("crew");
                for (int j = 0; j < crewList.length(); j++) {
                    CrewDetails crewDetails = new CrewDetails();
                    JSONObject crew = crewList.getJSONObject(i);
                    String crewID = crew.getString("ID");
                    String credit_ID = crew.getString("credit_id");
                    String name = crew.getString("name");
                    String department = crew.getString("department");
                    String job = crew.getString("job");
                    String profile_path = crew.getString("profile_path");
                    crewDetails.setId(crewID);
                    crewDetails.setCredit_id(credit_ID);
                    crewDetails.setName(name);
                    crewDetails.setDepartment(department);
                    crewDetails.setJob(job);
                    crewDetails.setProfilePath(profile_path);
                    crewDetailsList.add(crewDetails);
                }
                int episode_number = episodeNum.getInt("episode_number");
                JSONArray guestStarArray = episodeNum.getJSONArray("guest_stars");
                for (int k = 0; k < guestStarArray.length(); k++){
                    GuestStarsDetails guestStarsDetails = new GuestStarsDetails();
                    JSONObject guest_stars = guestStarArray.getJSONObject(k);
                    String id = guest_stars.getString("id");
                    String name = guest_stars.getString("name");
                    String credit_id = guest_stars.getString("credit_id");
                    String character = guest_stars.getString("character");
                    int order = guest_stars.getInt("order");
                    String profile_path = guest_stars.getString("profile_path");
                    guestStarsDetails.setId(id);
                    guestStarsDetails.setName(name);
                    guestStarsDetails.setCredit_id(credit_id);
                    guestStarsDetails.setCharacter(character);
                    guestStarsDetails.setOrder(order);
                    guestStarsDetails.setProfile_path(profile_path);
                    guestStarsDetailsList.add(guestStarsDetails);
                }
                String name = episodeNum.getString("name");
                String overview = episodeNum.getString("overview");
                String id = episodeNum.getString("id");
                String production_code = episodeNum.getString("production_code");
                String season_number = episodeNum.getString("season_number");
                String still_path = episodeNum.getString("still_path");
                double vote_average = episodeNum.getDouble("vote_average");
                int vote_count = episodeNum.getInt("vote_count");
                episodeBean.setAir_date(episode_air_date);
                episodeBean.setCrewDetails(crewDetailsList);
                episodeBean.setEpisode_number(episode_number);
                episodeBean.setGuestStarsDetails(guestStarsDetailsList);
                episodeBean.setName(name);
                episodeBean.setOverview(overview);
                episodeBean.setId(id);
                episodeBean.setProduction_code(production_code);
                episodeBean.setSeason_number(season_number);
                episodeBean.setStill_path(still_path);
                episodeBean.setVote_average(vote_average);
                episodeBean.setVote_count(vote_count);
                episodeList.add(episodeBean);

            }
            String name = root.getString("name");
            String overview = root.getString("overview");
            String id = root.getString("id");
            String poster_path = root.getString("poster_path");
            String season_number = root.getString("season_number");
            seasonDetailsBean.setAir_date(air_date);
            seasonDetailsBean.setEpisodes(episodeList);
            seasonDetailsBean.setName(name);
            seasonDetailsBean.setOverview(overview);
            seasonDetailsBean.setId(id);
            seasonDetailsBean.setPoster_path(poster_path);
            seasonDetailsBean.setSeason_number(season_number);
            seasonDetailsList.add(seasonDetailsBean);
        }catch (JSONException jex){

        }
        return  seasonDetailsList;
    }
}
