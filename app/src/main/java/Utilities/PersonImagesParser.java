package Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suny on 13/5/16.
 */
public class PersonImagesParser {
    private String json;

    public PersonImagesParser(String json) {
        this.json = json;
    }

    public List<PersonImagesBean> getImagesList() throws Exception{
        List<PersonImagesBean> list = new ArrayList<>();
        if(json == null) {
            throw new Exception("No or poor internet connection");
        }
        try {
            JSONObject root = new JSONObject(json);
            JSONArray profiles = root.getJSONArray("profiles");
            for (int i = 0; i < profiles.length(); i++) {
                PersonImagesBean bean = new PersonImagesBean();
                JSONObject image = profiles.getJSONObject(i);
                bean.setAspect_ratio((float) image.getDouble("aspect_ratio"));
                bean.setFile_path(image.getString("file_path"));
                bean.setHeight(image.getInt("height"));
                bean.setVote_average((float) image.getDouble("vote_average"));
                bean.setVote_count(image.getInt("vote_count"));
                bean.setWidth(image.getInt("width"));
                list.add(bean);
            }
        }catch (JSONException ex) {
            throw new Exception("Something went wrong! Try again later");
        }
        return list;
    }
}
