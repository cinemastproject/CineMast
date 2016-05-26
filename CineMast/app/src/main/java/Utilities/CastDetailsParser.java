package Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CastDetailsParser {
    String result;
    List<CastDetailsBean> castDetailsList = new ArrayList<>();

    public CastDetailsParser(String result){
        this.result = result;
    }

    public List<CastDetailsBean> getCastDetailsList(){
        try{
            JSONObject root = new JSONObject(result);
            String movie_id = root.getString("id");
            JSONArray castArray = root.getJSONArray("cast");
            for (int i = 0;i < castArray.length();i++){
                CastDetailsBean movieDetailsBean = new CastDetailsBean();
                JSONObject cast = castArray.getJSONObject(i);
                String cast_id = cast.getString("cast_id");
                String character = cast.getString("character");
                String credit_id = cast.getString("credit_id");
                String id = cast.getString("id");
                String name = cast.getString("name");
                String order = cast.getString("order");
                String profile_path = cast.getString("profile_path");

                movieDetailsBean.setCast_id(cast_id);
                movieDetailsBean.setCharacter(character);
                movieDetailsBean.setCredit_id(credit_id);
                movieDetailsBean.setId(id);
                movieDetailsBean.setName(name);
                movieDetailsBean.setOrder(order);
                movieDetailsBean.setProfile_path(profile_path);
                castDetailsList.add(movieDetailsBean);
            }
        }catch (JSONException jex){

        }
        return castDetailsList;

    }

}
