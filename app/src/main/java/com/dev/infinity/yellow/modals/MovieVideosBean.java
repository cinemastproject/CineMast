package com.dev.infinity.yellow.modals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieVideosBean implements Serializable{

    private List<Result> results = new ArrayList<>();

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public class Result {
        private String id;
        private String key;
        private String name;
        private String site;
        private int size;
        private String type;
        private String yid;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getYid() {
            return yid;
        }

        public void setYid(String yid) {
            this.yid = yid;
        }
    }
}
