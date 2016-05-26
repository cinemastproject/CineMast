package Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepjyoti on 14/5/16.
 */
public class EpisodeDetailsParser {
    String json;
    List<EpisodeDetailsBean> episodeDetailsList = new ArrayList<>();
    List<CrewDetails> crewDetailsList = new ArrayList<>();
    List<GuestStarsDetails> guestStarsDetailsList = new ArrayList<>();

    public EpisodeDetailsParser(String json){
        this.json = json;
    }

    public EpisodeDetailsBean getEpisodeDetail(){
        EpisodeDetailsBean episodeDetailsBean = new EpisodeDetailsBean();
        try{
            JSONObject root = new JSONObject(json);
            String air_date = root.getString("air_date");
            JSONArray crewArray = root.getJSONArray("crew");
            for (int i = 0; i < crewArray.length(); i++){
                CrewDetails crewDetails = new CrewDetails();
                JSONObject crew = crewArray.getJSONObject(i);
                String id = crew.getString("id");
                String credit_id = crew.getString("credit_id");
                String name = crew.getString("name");
                String department = crew.getString("department");
                String job = crew.getString("job");
                String profile_path = crew.getString("profile_path");
                crewDetails.setId(id);
                crewDetails.setName(name);
                crewDetails.setCredit_id(credit_id);
                crewDetails.setDepartment(department);
                crewDetails.setJob(job);
                crewDetails.setProfilePath(profile_path);
                crewDetailsList.add(crewDetails);
            }
            int episode_number = root.getInt("episode_number");
            JSONArray guestStarArray = root.getJSONArray("guest_stars");
            for (int i = 0; i < guestStarArray.length(); i++){
                GuestStarsDetails guestStarsDetails = new GuestStarsDetails();
                JSONObject guest_stars = guestStarArray.getJSONObject(i);
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
            String name = root.getString("name");
            String overview = root.getString("overview");
            String id = root.getString("id");
            String production_code = root.getString("production_code");
            int season_number = root.getInt("season_number");
            String still_path = root.getString("still_path");
            double vote_average = root.getDouble("vote_average");
            int vote_count = root.getInt("vote_count");
            episodeDetailsBean.setAir_date(air_date);
            episodeDetailsBean.setCrewDetailsList(crewDetailsList);
            episodeDetailsBean.setEpisode_number(episode_number);
            episodeDetailsBean.setGuestStarsDetailsList(guestStarsDetailsList);
            episodeDetailsBean.setName(name);
            episodeDetailsBean.setOverview(overview);
            episodeDetailsBean.setId(id);
            episodeDetailsBean.setProduction_code(production_code);
            episodeDetailsBean.setSeason_number(season_number);
            episodeDetailsBean.setStill_path(still_path);
            episodeDetailsBean.setVote_average(vote_average);
            episodeDetailsBean.setVote_count(vote_count);
        }catch (JSONException jex){

        }
        return episodeDetailsBean;

    }
}
