package Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PeopleParser {

    String jsonString;
    List<PeopleBean> list = new ArrayList<>();

    public PeopleParser(String jsonString) {
        super();
        this.jsonString = jsonString;
    }

    public List<PeopleBean> getPeopleList() throws Exception{
        if(jsonString == null)
            throw new Exception("No or poor internet connection");

        try{
            JSONObject root = new JSONObject(jsonString);
            int page = root.getInt("page");
            int total_result = root.getInt("total_results");
            int total_pages = root.getInt("total_pages");

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
                list.add(peopleBean);
            }
        }catch (JSONException ex){
            throw new Exception("Something wrong with server");
        }
        return list;
    }
}
