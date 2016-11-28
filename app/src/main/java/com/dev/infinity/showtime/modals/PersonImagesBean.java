package com.dev.infinity.showtime.modals;

import java.io.Serializable;
import java.util.List;

/**
 * Created by suny on 13/5/16.
 */
public class PersonImagesBean implements Serializable {

    private String id;
    private List<Profile> profiles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public class Profile {
        private float aspect_ratio;
        private String file_path;
        private int height;
        private float vote_average;
        private int vote_count;
        private int width;

        public float getAspect_ratio() {
            return aspect_ratio;
        }

        public void setAspect_ratio(float aspect_ratio) {
            this.aspect_ratio = aspect_ratio;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public float getVote_average() {
            return vote_average;
        }

        public void setVote_average(float vote_average) {
            this.vote_average = vote_average;
        }

        public int getVote_count() {
            return vote_count;
        }

        public void setVote_count(int vote_count) {
            this.vote_count = vote_count;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }
}
