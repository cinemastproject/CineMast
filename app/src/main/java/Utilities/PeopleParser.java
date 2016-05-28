package Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PeopleParser {

    String jsonString;
    PeopleResults peopleResults = new PeopleResults();
    List<PeopleBean> list = new ArrayList<>();

    public PeopleParser(String jsonString) {
        super();
        this.jsonString = jsonString;
    }

    public PeopleResults getPeopleList() throws Exception{
        if(jsonString == null)
            throw new Exception("No or poor internet connection");

        try{
            JSONObject root = new JSONObject(jsonString);
            int page = root.getInt("page");
            int total_result = root.getInt("total_results");
            int total_pages = root.getInt("total_pages");
            peopleResults.setPage(page);
            peopleResults.setTotalPages(total_pages);
            peopleResults.setTotalResults(total_result);

            JSONArray results = root.getJSONArray("results");
            for(int i = 0; i < results.length(); i++){
                JSONObject result = results.getJSONObject(i);
                String name = result.getString("name");
                String id = result.getString("id");
                String poster = result.getString("profile_path");
                PeopleBean peopleBean = new PeopleBean();
                peopleBean.setName(name);
                peopleBean.setId(id);
                peopleBean.setProfile_path(poster);
                peopleResults.getMoviesList().add(peopleBean);
            }
        }catch (JSONException ex){
            ex.printStackTrace();
            throw new Exception("Something went wrong! Try again later");
        }
        return peopleResults;
    }
}
