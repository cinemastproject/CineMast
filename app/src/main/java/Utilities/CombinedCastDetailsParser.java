package Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CombinedCastDetailsParser {
    String result;
    List<CombinedCastDetail> castDetailsList = new ArrayList<>();

    public CombinedCastDetailsParser(String result){
        this.result = result;
    }

    public List<CombinedCastDetail> getCastDetail(){
        try{
            JSONObject root = new JSONObject(result);
            String movie_id = root.getString("id");
            JSONArray castArray = root.getJSONArray("cast");
            for (int i = 0;i < castArray.length();i++){
                CombinedCastDetail castDetailsBean = new CombinedCastDetail();
                JSONObject cast = castArray.getJSONObject(i);
                String character = cast.getString("character");
                String credit_id = cast.getString("credit_id");
                String id = cast.getString("id");
                String name = cast.getString("original_title");
                String title = cast.getString("title");
                String profile_path = cast.getString("poster_path");
                String media_type = cast.getString("media_type");

                castDetailsBean.setCharacter(character);
                castDetailsBean.setCredit_id(credit_id);
                castDetailsBean.setId(id);
                castDetailsBean.setMedia_type(media_type);
                castDetailsBean.setOriginal_title(name);
                castDetailsBean.setPoster_path(profile_path);
                castDetailsList.add(castDetailsBean);
            }
        }catch (JSONException jex){

        }
        return castDetailsList;

    }

}
