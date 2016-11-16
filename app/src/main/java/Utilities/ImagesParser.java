package Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ImagesParser {
    private String result;

    public ImagesParser(String result) {
        this.result = result;
    }

    public List<String> getImages() {
        List<String> images = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(result);
            JSONArray posters = root.getJSONArray("posters");
            for(int j = 0; j < posters.length(); j++) {
                if(j >= 5)
                    break;
                JSONObject image = posters.getJSONObject(j);
                String path = image.getString("file_path");
                images.add(path);
            }
            JSONArray backDrops = root.getJSONArray("backdrops");
            for(int i = 0; i < backDrops.length(); i++) {
                if(i >= 5)
                    break;
                JSONObject image = backDrops.getJSONObject(i);
                String path = image.getString("file_path");
                images.add(path);
            }
        }catch (JSONException ex) {
            ex.printStackTrace();
        }

        return images;
    }

    public List<String> getAllImages() {
        List<String> images = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(result);
            JSONArray posters = root.getJSONArray("posters");
            for(int j = 0; j < posters.length(); j++) {
                JSONObject image = posters.getJSONObject(j);
                String path = image.getString("file_path");
                images.add(path);
            }
            JSONArray backDrops = root.getJSONArray("backdrops");
            for(int i = 0; i < backDrops.length(); i++) {
                JSONObject image = backDrops.getJSONObject(i);
                String path = image.getString("file_path");
                images.add(path);
            }
        }catch (JSONException ex) {
            ex.printStackTrace();
        }

        return images;
    }
}